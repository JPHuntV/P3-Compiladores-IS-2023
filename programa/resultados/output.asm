.data
    num: .word 0

.text
    .globl main

main:
    li $a0, 201
    jal funcB
    sw $v0, num
    move $a0, $v0
    li $v0, 1
    syscall

    li $v0, 10
    syscall

funcB:
    sw $ra, 0($sp)
    addiu $sp, $sp, -4

    lw $t0, 0($a0)
    li $t1, 100
    ble $t0, $t1, else #if not(num> 100.0)

    lw $t0, 0($a0)
    li $t1, 50
    sub $t0, $t0, $t1
    move $a0, $t0
    jal funcB

    lw $ra, 4($sp)
    addiu $sp, $sp, 4
    move $v0, $a0
    jr $ra

else:
    move $v0, $a0
    jr $ra
