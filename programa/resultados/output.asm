.data
vara: .word 0
varb: .word 0
varc: .word 0
.text
.globl main
main:
#declaracion de int
li $t0, 6
sw $t0, vara
#declaracion de int
li $t1, 2
sw $t1, varb
#declaracion de int
lw $t2, vara
lw $t3, varb
#op = +
add $t4, $t2, $t3
sw $t4, varc
lw $t5, varc
#print
move $a0, $t5
li $v0, 1
syscall
li $t6, 0
#return
li $v0, 10
syscall
