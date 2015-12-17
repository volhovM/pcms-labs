output = """start: _s
accept: _ac
reject: _rj
blank: _
_s 0 -> _add_plus_toend 0 ^
_s 1 -> _add_plus_toend 1 ^
_s 2 -> _add_plus_toend 2 ^
_s _ -> _add_plus_toend _ ^
_s + -> _add_plus_toend + ^
_add_plus_toend 0 -> _add_plus_toend 0 >
_add_plus_toend 1 -> _add_plus_toend 1 >
_add_plus_toend 2 -> _add_plus_toend 2 >
_add_plus_toend _ -> _add_zero_toend + >
_add_plus_toend + -> _add_plus_toend + >
_add_zero_toend _ -> _left_to_plus 0 <
_decrement_left_pre 0 -> _decrement_left_carry 2 <
_decrement_left_pre 1 -> _right_to_plus 0 >
_decrement_left_pre 2 -> _right_to_plus 1 >
_decrement_left_carry 0 -> _decrement_left_carry 2 <
_decrement_left_carry 1 -> _right_to_plus 0 >
_decrement_left_carry 2 -> _right_to_plus 1 >
_decrement_left_carry _ -> _right_to_plus_finish _ >
_right_to_plus 0 -> _right_to_plus 0 >
_right_to_plus 1 -> _right_to_plus 1 >
_right_to_plus 2 -> _right_to_plus 2 >
_right_to_plus _ -> _right_to_plus _ >
_right_to_plus + -> _increment_right + >
_increment_right 0 -> _increment_right 0 >
_increment_right 1 -> _increment_right 1 >
_increment_right 2 -> _increment_right 2 >
_increment_right _ -> _increment_left_pre _ <
_increment_right + -> _increment_right + >
_increment_left_pre 0 -> _left_to_plus 1 <
_increment_left_pre 1 -> _increment_left_carry 0 <
_increment_left_carry 0 -> _left_to_plus 1 <
_increment_left_carry 1 -> _increment_left_carry 0 <
_increment_left_carry + -> _rmove_right_pre + >
_rmove_right_pre 0 -> _rmove_right_blanking 1 >
_rmove_right_pre 1 -> _rmove_right_blanking 1 >
_rmove_right_pre 2 -> _rmove_right_blanking 1 >
_rmove_right_pre _ -> _rmove_right_blanking 1 >
_rmove_right_pre + -> _rmove_right_blanking 1 >
_rmove_right_blanking 0 -> _rmove_right_blanking 0 >
_rmove_right_blanking 1 -> _rmove_right_blanking 0 >
_rmove_right_blanking 2 -> _rmove_right_blanking 0 >
_rmove_right_blanking _ -> _left_to_plus 0 <
_rmove_right_blanking + -> _rmove_right_blanking 0 >
_left_to_plus 0 -> _left_to_plus 0 <
_left_to_plus 1 -> _left_to_plus 1 <
_left_to_plus 2 -> _left_to_plus 2 <
_left_to_plus _ -> _left_to_plus _ <
_left_to_plus + -> _decrement_left_pre + <
_right_to_plus_finish 0 -> _right_to_plus_finish _ >
_right_to_plus_finish 1 -> _right_to_plus_finish _ >
_right_to_plus_finish 2 -> _right_to_plus_finish _ >
_right_to_plus_finish _ -> _right_to_plus_finish _ >
_right_to_plus_finish + -> _ac _ >
"""    
f = open("convertto2.out", "w") 
f.write(output)                         
f.close()                               
exit(0)
