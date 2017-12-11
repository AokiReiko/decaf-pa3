VTABLE(_Main) {
    <empty>
    Main
}

FUNCTION(_Main_New) {
memo ''
_Main_New:
    _T0 = 4
    parm _T0
    _T1 =  call _Alloc
    _T2 = VTBL <_Main>
    *(_T1 + 0) = _T2
    return _T1
}

FUNCTION(main) {
memo ''
main:
    _T7 = 1
    _T5 = _T7
    _T8 = "wow!"
    _T4 = _T8
    _T9 = 3
    _T3 = _T9
    _T10 = 1
    _T11 = ! _T10
    _T12 = 3
    _T13 = (_T11 + _T12)
    _T6 = _T13
    if (_T5 == 0) branch _L10
    _T14 = 5
    _T15 = (_T3 * _T14)
    _T3 = _T15
_L10:
    parm _T5
    call _PrintBool
    _T16 = " "
    parm _T16
    call _PrintString
    parm _T3
    call _PrintInt
}

