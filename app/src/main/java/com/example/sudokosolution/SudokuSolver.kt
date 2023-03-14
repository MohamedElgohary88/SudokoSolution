package com.example.sudokosolution

class SudokuSolver(private val board: Array<IntArray>) {

    companion object {
        private const val BOARD_SIZE = 9
        private const val BOX_SIZE = 3
        private const val EMPTY_CELL = 0
    }

    fun solve(): Boolean {
        for (row in 0 until BOARD_SIZE) {
            for (col in 0 until BOARD_SIZE) {
                if (board[row][col] == EMPTY_CELL) {
                    for (num in 1..9) {
                        if (isValidMove(row, col, num)) {
                            board[row][col] = num
                            if (solve()) {
                                return true
                            }
                            board[row][col] = EMPTY_CELL
                        }
                    }
                    return false
                }
            }
        }
        return true
    }

    private fun isValidMove(row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until BOARD_SIZE) {
            if (board[row][i] == num) {
                return false
            }
            if (board[i][col] == num) {
                return false
            }
        }
        val boxRowStart = row - row % BOX_SIZE
        val boxColStart = col - col % BOX_SIZE
        for (i in boxRowStart until boxRowStart + BOX_SIZE) {
            for (j in boxColStart until boxColStart + BOX_SIZE) {
                if (board[i][j] == num) {
                    return false
                }
            }
        }
        return true
    }
}
