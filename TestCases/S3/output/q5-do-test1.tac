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
    _T6 = 0
    _T3 = _T6
    _T7 = 1
    _T4 = _T7
    _T8 = 2
    _T5 = _T8
    parm _T3
    call _PrintInt
    parm _T4
    call _PrintInt
    parm _T5
    call _PrintInt
}

