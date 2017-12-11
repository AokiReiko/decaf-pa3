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
    _T11 = 1
    _T6 = _T11
    parm _T6
    call _PrintInt
    _T12 = "\n"
    parm _T12
    call _PrintString
    _T13 = 3
    _T14 = ! _T13
    _T15 = 12
    _T16 = (_T14 + _T15)
    _T3 = _T16
    _T17 = "\n"
    parm _T17
    call _PrintString
    _T18 = 3
    _T19 = ! _T18
    _T20 = 45
    _T21 = (_T19 + _T20)
    _T5 = _T21
    _T22 = ! _T3
    _T7 = _T22
    _T23 = ! _T3
    _T8 = _T23
    parm _T7
    call _PrintInt
    parm _T8
    call _PrintInt
    _T24 = "\n"
    parm _T24
    call _PrintString
    _T25 = (_T7 + _T8)
    _T26 = ! _T25
    _T4 = _T26
    _T27 = "\n"
    parm _T27
    call _PrintString
    _T28 = "\n"
    parm _T28
    call _PrintString
    _T29 = "\n"
    parm _T29
    call _PrintString
    _T30 = (_T3 + _T4)
    _T5 = _T30
    _T31 = ! _T7
    _T32 = (_T3 + _T31)
    _T5 = _T32
    _T33 = 0
    _T34 = (_T3 + _T33)
    _T5 = _T34
    _T35 = 0
    _T36 = ! _T7
    _T37 = (_T35 + _T36)
    _T5 = _T37
    _T38 = 4
    _T39 = (_T38 + _T7)
    _T10 = _T39
    parm _T10
    call _PrintInt
    _T40 = "\n"
    parm _T40
    call _PrintString
    _T41 = "\n"
    parm _T41
    call _PrintString
    _T42 = (_T3 * _T4)
    _T5 = _T42
    _T43 = ! _T7
    _T44 = (_T3 * _T43)
    _T5 = _T44
    _T45 = 0
    _T46 = (_T3 * _T45)
    _T5 = _T46
    _T47 = 0
    _T48 = ! _T7
    _T49 = (_T47 * _T48)
    _T5 = _T49
    _T50 = 4
    _T51 = (_T50 * _T7)
    _T10 = _T51
    parm _T10
    call _PrintInt
    _T52 = "\n"
    parm _T52
    call _PrintString
    _T53 = "\n"
    parm _T53
    call _PrintString
}

