.data
float0: .float 1.2
float1: .float 1.2
float2: .float 2.3
ttt: .space 1
var: .space 1
i: .word 0
otrasS: .word 0
xxx: .word 0
otrasSsd: .word 0
prueba: .word 0
otras: .word 0
otrase: .word 0
var: .word 0
i: .word 0
a: .space 28
b: .space 16
float3: .float 6.7
.text
main:
test:
#declaracion de float
#declaracion de temporal
#largo = 3
l.s $f0, float0
#declaracion de temporal
#largo = 3
sb $t0, t0
#declaracion de temporal
#largo = 3
l.s $f1, float1
#declaracion de temporal
#largo = 3
l.s $f2, float2
#declaracion de temporal
#largo = 5
#op = +
add $t0, $t-2, $t-1
move $t1, $t0
#declaracion de temporal
#largo = 3
sb $t1, t3
#declaracion de char
#declaracion de temporal
#largo = 3
li $t1, 'a'
#declaracion de temporal
#largo = 3
li $t2, 'b'
#declaracion de temporal
#largo = 5
#op = +
add $t3, $t1, $t2
move $t4, $t3
#declaracion de temporal
#largo = 3
sb $t4, t6
#declaracion de char
#declaracion de temporal
#largo = 3
li $t4, 'a'
sb $t4, var
#declaracion de temporal
#largo = 3
li $t5, var
#declaracion de temporal
#largo = 3
sb $t6, ttt
#declaracion de temporal
#largo = 5
#op = +
add $t6, $t4, $t5
move $t7, $t6
sb $t6, var
_test_while1_eval:
#declaracion de temporal
#largo = 3
li $t7, a
#declaracion de temporal
#largo = 3
li $t8, b
#declaracion de temporal
#largo = 5
#op = >
sgt $t9, $t7, $t8
#if
beqz $t9, _test_while1_body
#goto
j _test_while1_end
_test_while1_body:
_test_for1_init:
#declaracion de int
#declaracion de temporal
#largo = 3
li $t10, 0
sb $t10, i
_test_for1_eval:
#declaracion de temporal
#largo = 3
li $t11, i
#declaracion de temporal
#largo = 3
li $t12, a
#declaracion de temporal
#largo = 5
#op = >
sgt $t13, $t11, $t12
#if
beqz $t13, _test_for1_body
#goto
j _test_for1_end
_test_for1_update:
#declaracion de temporal
#largo = 3
lw $t14, i
addi $t15, $t14, 1
sw $t15, i
#goto
j _test_for1_eval
_test_for1_body:
#declaracion de temporal
#largo = 3
li $t15, a
#declaracion de temporal
#largo = 3
li $t16, 1
#declaracion de temporal
#largo = 5
#op = -
sub $t17, $t15, $t16
move $t18, $t17
sb $t17, a
_test_if1_eval:
#declaracion de temporal
#largo = 3
li $t18, a
#declaracion de temporal
#largo = 3
li $t19, 0
#declaracion de temporal
#largo = 5
#op = ==
seq $t20, $t18, $t19
#if
beqz $t20, _test_if1_body
#goto
j _test_if1_else
_test_if1_body:
#declaracion de temporal
#largo = 5
#op = al
param t25
call print, 1
#goto
j _test_if1_end
_test_if1_elifStm:
_test_elif1_eval:
#declaracion de temporal
#largo = 3
sb $t21, true
#if
beqz $t20, _test_elif1_body
#goto
j _test_elif1_else
_test_elif1_body:
#declaracion de temporal
#largo = 3
li $t21, a
param t28
call print, 1
#goto
j _test_for1_end
#goto
j _test_elif1_end
_test_elif1_else:
_test_elif2_eval:
#declaracion de temporal
#largo = 3
li $t22, false
#declaracion de temporal
#largo = 3
sb $t23, true
#declaracion de temporal
#largo = 5
#op = ^
and $t23, $t21, $t22
#if
beqz $t23, _test_elif2_body
#goto
j _test_elif2_else
_test_elif2_body:
#declaracion de temporal
#largo = 3
li $t24, a
param t33
call read, 1
#goto
j _test_for1_end
#goto
j _test_elif2_end
_test_elif2_else:
#goto
j _test_elif2_end
_test_elif2_end:
#goto
j _test_elif1_end
_test_elif1_end:
_test_if1_else:
_test_else1_body:
#declaracion de temporal
#largo = 3
li $t25, 0
sb $t25, i
#goto
j _test_else1_end
_test_else1_end:
#goto
j _test_if1_end
_test_if1_end:
#goto
j _test_for1_update
_test_for1_end:
#goto
j _test_while1_eval
_test_while1_end:
#declaracion de temporal
#largo = 3
li $t26, a
#declaracion de temporal
#largo = 3
li $t27, b
#declaracion de temporal
#largo = 5
#op = +
add $t28, $t26, $t27
move $t29, $t28
return t38
miFunc:
#declaracion de int
#declaracion de temporal
#largo = 3
li $t29, 1
sb $t29, otrasS
#declaracion de temporal
#largo = 3
li $t30, 2
#declaracion de temporal
#largo = 3
li $t31, otrasS
#declaracion de temporal
#largo = 5
#op = +
add $t32, $t30, $t31
move $t33, $t32
sb $t32, otrasS
#declaracion de int
#declaracion de temporal
#largo = 3
li $t33, otrasS
#declaracion de temporal
#largo = 3
li $t34, 2
#declaracion de temporal
#largo = 5
#op = +
add $t35, $t33, $t34
move $t36, $t35
sb $t35, xxx
#declaracion de int
#declaracion de temporal
#largo = 3
li $t36, 2
#declaracion de temporal
#largo = 3
li $t37, 3
#declaracion de temporal
#largo = 3
li $t38, 4
#declaracion de temporal
#largo = 5
#op = +
add $t39, $t37, $t38
move $t40, $t39
#declaracion de temporal
#largo = 5
#op = +
add $t40, $t38, $t39
move $t41, $t40
sb $t40, otrasSsd
#declaracion de int
#declaracion de temporal
#largo = 3
li $t41, 1
#declaracion de temporal
#largo = 3
li $t42, otrasS
param t51
param t52
#declaracion de temporal
#largo = 5
#op = test,
sb $t42, prueba
#declaracion de int
#declaracion de temporal
#largo = 3
li $t43, -dif
sb $t43, otras
#declaracion de int
#declaracion de temporal
#largo = 3
li $t44, -2
sb $t44, otrase
dataString str
#declaracion de temporal
#largo = 5
#op = $%&/#$&)
sb $t44, str
#declaracion de int
#declaracion de temporal
#largo = 3
li $t45, 8
sb $t45, var
_miFunc_for1_init:
#declaracion de int
#declaracion de temporal
#largo = 3
li $t46, 0
sb $t46, i
_miFunc_for1_eval:
#declaracion de temporal
#largo = 3
li $t47, i
#declaracion de temporal
#largo = 3
li $t48, var
#declaracion de temporal
#largo = 5
#op = >
sgt $t49, $t47, $t48
#if
beqz $t49, _miFunc_for1_body
#goto
j _miFunc_for1_end
_miFunc_for1_update:
#declaracion de temporal
#largo = 3
lw $t50, i
addi $t51, $t50, 1
sw $t51, i
#goto
j _miFunc_for1_eval
_miFunc_for1_body:
_miFunc_doWhile1_body:
#declaracion de temporal
#largo = 3
li $t51, var
#declaracion de temporal
#largo = 3
li $t52, 1
#declaracion de temporal
#largo = 5
#op = -
sub $t53, $t51, $t52
move $t54, $t53
sb $t53, var
_miFunc_doWhile1_eval:
#declaracion de temporal
#largo = 3
li $t54, var
#declaracion de temporal
#largo = 3
li $t55, 12
#declaracion de temporal
#largo = 5
#op = >
sgt $t56, $t54, $t55
#declaracion de temporal
#largo = 3
li $t57, 34
#declaracion de temporal
#largo = 3
li $t58, 34
#declaracion de temporal
#largo = 5
#op = +
add $t59, $t57, $t58
move $t60, $t59
#declaracion de temporal
#largo = 3
li $t60, 12
#declaracion de temporal
#largo = 5
#op = >
sgt $t61, $t59, $t60
#declaracion de temporal
#largo = 5
#op = #
or $t62, $t60, $t61
#if
beqz $t62, _miFunc_doWhile1_body
#goto
j _miFunc_doWhile1_end
_miFunc_doWhile1_end:
_miFunc_if2_eval:
#declaracion de temporal
#largo = 3
li $t63, var
#declaracion de temporal
#largo = 3
li $t64, 0
#declaracion de temporal
#largo = 5
#op = ==
seq $t65, $t63, $t64
#if
beqz $t65, _miFunc_if2_body
#goto
j _miFunc_if2_else
_miFunc_if2_body:
#declaracion de temporal
#largo = 5
#op = al
param t78
call print, 1
#goto
j _miFunc_if2_end
_miFunc_if2_elifStm:
_miFunc_elif3_eval:
#declaracion de temporal
#largo = 3
sb $t66, true
#if
beqz $t65, _miFunc_elif3_body
#goto
j _miFunc_elif3_else
_miFunc_elif3_body:
#declaracion de temporal
#largo = 3
li $t66, var
param t81
call print, 1
#goto
j _miFunc_elif3_end
_miFunc_elif3_else:
_miFunc_elif4_eval:
#declaracion de temporal
#largo = 3
li $t67, false
#declaracion de temporal
#largo = 3
sb $t68, true
#declaracion de temporal
#largo = 5
#op = ^
and $t68, $t66, $t67
#if
beqz $t68, _miFunc_elif4_body
#goto
j _miFunc_elif4_else
_miFunc_elif4_body:
#declaracion de temporal
#largo = 3
li $t69, var
param t86
call read, 1
#goto
j _miFunc_for1_end
#goto
j _miFunc_elif4_end
_miFunc_elif4_else:
#goto
j _miFunc_elif4_end
_miFunc_elif4_end:
#goto
j _miFunc_elif3_end
_miFunc_elif3_end:
#goto
j _miFunc_if2_end
_miFunc_if2_end:
#goto
j _miFunc_for1_update
_miFunc_for1_end:
#declaracion de temporal
#largo = 3
li $t70, 1
#declaracion de temporal
#largo = 3
li $t71, otrasS
param t88
param t89
call test, 2
return t90
miOtraFun:
#declaracion de temporal
#largo = 3
sb $t72, true
return t91
main:
#declaracion de array
#declaracion de temporal
#largo = 3
li $t72, 1
#declaracion de temporal
#largo = 3
li $t73, 2
sb $t73, a[0]
sb $t73, a[1]
#declaracion de array
sb $t73, b
#declaracion de temporal
#largo = 3
li $t74, "hola"
param t94
call print, 1
#declaracion de temporal
#largo = 3
li $t75, 34
param t96
call print, 1
#declaracion de temporal
#largo = 3
l.s $f3, float3
param t98
call print, 1
#declaracion de temporal
#largo = 3
li $t76, 1
return t100
