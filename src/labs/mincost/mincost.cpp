#include <vector>
#include <fstream>
#include <iostream>
#include <vector>
#include <queue>
#include <set>
#include <algorithm>
using namespace std;

const int INF = 0xFFFFFFFF / 3;

struct edge {
    int next, inv, flow, cap, cost;

    edge(int next, int inv, int cap, int cost) :
	next(next), inv(inv), flow(0), cap(cap), cost(cost) {}
};

struct graph {
    int n;
    vector<vector<edge> > edges;
    vector<int> d, phi, from;
    vector<bool> used;

    graph(int n) :
	n(n),
	edges(n),
	d(n),
	phi(n),
	from(n),
	used(n)

    {}

    void add_edge(int from, int to, int cap, int cost) {
	edges[from].push_back(edge(to, edges[to].size(), cap, cost));
	edges[to].push_back(edge(from, edges[from].size() - 1, 0, -cost));
    }

    bool dijkstra(int t) {
	for(;;) {
	    int v0 = -1;
	    for(int v = 0; v < n; v++)
		if(!used[v] &&
		   d[v] < INF &&
		   (v0 < 0 || d[v0] > d[v]))
		    v0 = v;
	    if(v0 < 0) break;
	    //cout << "dijkstra cycle" << endl;
	    used[v0] = true;
	    for(int e = 0; e < edges[v0].size(); e++)
		if(edges[v0][e].cap > edges[v0][e].flow) {
		    int to = edges[v0][e].next;
		    int opt = d[v0] + edges[v0][e].cost + phi[v0] - phi[to];
		    if(!used[to] && d[to] > opt) {
			d[to] = opt;
			from[to] = edges[v0][e].inv;
		    }
		}
	}
	return used[t];
    }

    template <typename T>
    void dump(vector<T> vec) {
	for (int i = 0 ; i < vec.size(); i++) cout << vec[i] << " ";
	cout << endl;
    }

    long long min_cost_of_max_flow(int s, int t) {
	fill(phi.begin(), phi.end(), INF);
	phi[s] = 0;
	//	dump(phi);
	//initial potentials with ford-bellman
	for(int N = 0; N < n; N++)
	    for(int v = 0; v < n; v++)
		if(phi[v] < INF)
		    for(int e = 0; e < edges[v].size(); e++)
			if(edges[v][e].cap > 0 &&
			   phi[edges[v][e].next] > phi[v] + edges[v][e].cost)
			    phi[edges[v][e].next] = phi[v] + edges[v][e].cost;

	//dump(phi);
	int result_flow = 0;
	long long result_cost = 0;

	for(;;) {
	    //cout << "algo cycle" << endl;
	    fill(used.begin(), used.end(), false);
	    fill(d.begin(), d.end(), INF);
	    d[s] = 0;

	    //dijkstra
	    if(!dijkstra(t)) break;
	    // cout << "dijkstra suceeded" << endl;
	    for(int i = 0; i < n; i++)
		phi[i] += used[i] ? d[i] : d[t];

	    int curr_flow = INF;
	    long long curr_cost = 0;
	    for(int i = t; i != s; ) {
		int v = edges[i][from[i]].next;
		int e = edges[i][from[i]].inv;
		curr_flow = min(curr_flow, edges[v][e].cap - edges[v][e].flow);
		curr_cost += 0ll + edges[v][e].cost;
		i = v;
	    }
	    // correcting edges
	    for(int i = t; i != s; ) {
		int v = edges[i][from[i]].next;
		int e = edges[i][from[i]].inv;
		edges[v][e].flow += curr_flow;
		edges[i][from[i]].flow -= curr_flow;
		i = v;
	    }
	    result_flow += curr_flow;
	    result_cost += 1ll * curr_flow * curr_cost;
	}
	return result_cost;
    }
};


int main() {
    ios::sync_with_stdio(0);
    ifstream fin("mincost.in");
    ofstream fout;
    fout.open("mincost.out");
    int n, m;
    fin >> n >> m;
    graph g(n);
    for (int i = 0; i < m; i++) {
	int a, b, c, cost;
	fin >> a >> b >> c >> cost;
	//cout << a - 1 << b - 1 << c << cost << endl;
	g.add_edge(a - 1, b - 1, c, cost);
    }
    // cout << "test" << endl;
    fout << g.min_cost_of_max_flow(0, n - 1);
    fout.close();
}
