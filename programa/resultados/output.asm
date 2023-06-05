.data
vara: .word 0
varb: .word 0
varc: .word 0
.text
.globl main
main:
#declaracion de int
#declaracion de temporal
#largo = 3
li $t0, 1
sw $t0, vara
#declaracion de int
#declaracion de temporal
#largo = 3
li $t1, 2
sw $t1, varb
#declaracion de int
#declaracion de temporal
#largo = 3
lw $t2, vara
#declaracion de temporal
#largo = 3
lw $t3, varb
#declaracion de temporal
#largo = 5
#op = +
add $t4, $t2, $t3
sw $t4, varc
#declaracion de temporal
#largo = 3
lw $t5, varc
#print
move $a0, $t5
li $v0, 1
syscall
#declaracion de temporal
#largo = 3
li $t6, 0
#return
li $v0, 10
syscall
