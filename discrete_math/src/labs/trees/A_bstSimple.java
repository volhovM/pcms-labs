package labs.trees;
/**
 *   Created by volhovm on 01.04.14
 */

import java.io.*;
import java.util.StringTokenizer;

public class A_bstSimple {
    public static void main(String[] args) throws IOException {
        scin = new FastScanner(new File("bst.in"));
        scout = new PrintWriter(new File("bst.out"));
        BST bst = new BST();
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                String query = scin.next();
                switch (query) {
                    case "put":
                        bst.insert(scin.nextInt());
                        break;
                    case "exists":
                        scout.println(bst.exists(scin.nextInt()));
                        break;
                    case "delete":
                        bst.delete(scin.nextInt());
                        break;
                    case "next":
                        scout.println(bst.next(scin.nextInt()));
                        break;
                    case "prev":
                        scout.println(bst.prev(scin.nextInt()));
                        break;
                }
            }
        } catch (IOException ignored) {
        }
        scout.close();
    }

    static class BST {
        private class Node {
            Node leftSon, rightSon, parent;
            int value;
            boolean isRightSon;

            private Node(Node parent, Node leftSon, Node rightSon, int value, boolean isRightSon) {
                this.leftSon = leftSon;
                this.rightSon = rightSon;
                this.parent = parent;
                this.value = value;
                this.isRightSon = isRightSon;
            }
        }

        Node head = null;

        public void insert(int x) {
            if (head == null) {
                head = new Node(null, null, null, x, true);
            } else {
                Node currentNode = head;
                while (true) {
                    if (x == currentNode.value) {
                        break;
                    } else if (x < currentNode.value) {
                        if (currentNode.leftSon != null) {
                            currentNode = currentNode.leftSon;
                        } else {
                            currentNode.leftSon = new Node(currentNode, null, null, x, false);
                            break;
                        }
                    } else if (x > currentNode.value) {
                        if (currentNode.rightSon != null) {
                            currentNode = currentNode.rightSon;
                        } else {
                            currentNode.rightSon = new Node(currentNode, null, null, x, true);
                            break;
                        }
                    }
                }
            }
        }

        private Node findAndReturn(int x) {
            if (head == null) {
                return null;
            } else {
                Node currentNode = head;
                while (true) {
                    if (x == currentNode.value) {
                        return currentNode;
                    } else if (x < currentNode.value) {
                        if (currentNode.leftSon != null) {
                            currentNode = currentNode.leftSon;
                        } else {
                            return null;
                        }
                    } else if (x > currentNode.value) {
                        if (currentNode.rightSon != null) {
                            currentNode = currentNode.rightSon;
                        } else {
                            return null;
                        }
                    }
                }
            }
        }

        public boolean exists(int x) {
            Node node = findAndReturn(x);
            return node != null;
        }

        public void delete(int x) {
            Node node = findAndReturn(x);
            if (node == null) {
                return;
            }
            if (node.rightSon == null && node.leftSon == null) {
                if (node == head) {
                    head = null;
                } else if (node.isRightSon) { // node = null doesn't work. Strange!
                    node.parent.rightSon = null;
                } else {
                    node.parent.leftSon = null;
                }
                node.parent = null;
            } else if (node.rightSon == null || node.leftSon == null) {
                Node nodeC = (node.rightSon == null) ? node.leftSon : node.rightSon;
                node.value = nodeC.value;
                node.rightSon = nodeC.rightSon;
                node.leftSon = nodeC.leftSon;
            } else {
                Node nextNode = nextNode(x);
                int value = nextNode.value;
                delete(nextNode.value);
                node.value = value;
            }
        }

        public String next(int x) {
            Node n = nextNode(x);
            return (n == null) ? "none" : String.valueOf(n.value);
        }

        private Node nextNode(int x) {
            if (head == null) {
                return null;
            } else {
                Node currentNode = head;
                Node minNode;
                while (currentNode != null) {
                    if (x >= currentNode.value) {
                        currentNode = currentNode.rightSon;
                    } else {
                        minNode = currentNode;
                        currentNode = currentNode.leftSon;
                        while (currentNode != null) {
                            if (x < currentNode.value) {
                                minNode = currentNode;
                                currentNode = currentNode.leftSon;
                            } else {
                                currentNode = currentNode.rightSon;
                            }
                        }
                        return minNode;
                    }
                }
                return null;
            }
        }

        public String prev(int x) {
            Node z = prevNode(x);
            return (z == null) ? "none" : String.valueOf(z.value);
        }

        private Node prevNode(int x) {
            if (head == null) {
                return null;
            } else {
                Node currentNode = head;
                Node maxNode;
                while (currentNode != null) {
                    if (x <= currentNode.value) {
                        currentNode = currentNode.leftSon;
                    } else {
                        maxNode = currentNode;
                        currentNode = currentNode.rightSon;
                        while (currentNode != null) {
                            if (x > currentNode.value) {
                                maxNode = currentNode;
                                currentNode = currentNode.rightSon;
                            } else {
                                currentNode = currentNode.leftSon;
                            }
                        }
                        return maxNode;
                    }
                }
                return null;
            }
        }
    }

    public static FastScanner scin;
    public static PrintWriter scout;

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) throws IOException {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    throw new IOException("EOF", e);
                }

            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
