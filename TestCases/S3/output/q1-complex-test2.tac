VTABLE(_Mac) {
    <empty>
    Mac
    _Mac.Crash;
}

VTABLE(_Main) {
    <empty>
    Main
}

FUNCTION(_Mac_New) {
memo ''
_Mac_New:
    _T4 = 12
    parm _T4
    _T5 =  call _Alloc
    _T6 = 0
    *(_T5 + 4) = _T6
    *(_T5 + 8) = _T6
    _T7 = VTBL <_Mac>
    *(_T5 + 0) = _T7
    return _T5
}

FUNCTION(_Main_New) {
memo ''
_Main_New:
    _T8 = 4
    parm _T8
    _T9 =  call _Alloc
    _T10 = VTBL <_Main>
    *(_T9 + 0) = _T10
    return _T9
}

FUNCTION(_Mac.Crash) {
memo '_T0:4 _T1:8 _T2:12 _T3:16'
_Mac.Crash:
    _T11 = *(_T0 + 8)
    *(_T0 + 8) = _T1
    _T12 = *(_T0 + 4)
    *(_T0 + 4) = _T2
    _T13 = *(_T0 + 8)
    parm _T13
    call _PrintInt
    _T14 = "\n"
    parm _T14
    call _PrintString
    parm _T3
    call _PrintInt
    _T15 = "\n"
    parm _T15
    call _PrintString
}

FUNCTION(main) {
memo ''
main:
    _T17 =  call _Mac_New
    _T16 = _T17
    _T18 = 2
    _T19 = 3
    _T20 = ! _T19
    _T21 = 4
    _T22 = (_T20 + _T21)
    _T23 = 5
    parm _T16
    parm _T18
    parm _T22
    parm _T23
    _T24 = *(_T16 + 0)
    _T25 = *(_T24 + 8)
    call _T25
}

