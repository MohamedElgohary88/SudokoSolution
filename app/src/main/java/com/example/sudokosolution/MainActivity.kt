package com.example.sudokosolution

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var gridLayout: GridLayout
    private lateinit var solveButton: Button
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         initialization()

        solveButton.setOnClickListener { solveSudoku() }
        clearButton.setOnClickListener { clearGrid() }
    }

    private fun initialization() {
        gridLayout = findViewById(R.id.grid_layout)
        solveButton = findViewById(R.id.solve_button)
        clearButton = findViewById(R.id.clear_button)
    }


    private fun solveSudoku(): Boolean {
        val sudoku = Array(9) { IntArray(9) }
        var index = 0
        var nonEmptyCells = 0 // count of non-empty cells

        // Extract the puzzle from the UI
        for (row in 0..8) {
            for (col in 0..8) {
                val cell = gridLayout.getChildAt(index) as EditText
                val value = cell.text.toString().trim()

                if (value.isNotEmpty() && !Character.isDigit(value[0])) {
                    Toast.makeText(this, "Invalid character in cell (${row + 1}, ${col + 1})",
                        Toast.LENGTH_SHORT).show()
                    return false
                }
                sudoku[row][col] = if (value.isNotEmpty()) {
                    nonEmptyCells++
                    value.toInt()
                } else {
                    0
                }
                index++
            }
        }

        // Check if the number of non-empty cells is less than 17
        if (nonEmptyCells < 17) {
            Toast.makeText(this, "Please add 17 numbers minimum", Toast.LENGTH_SHORT).show()
            return false
        }

        // Solve the puzzle
        val solver = SudokuSolver(sudoku)
        if (!solver.solve()) {
            Toast.makeText(this, "Puzzle cannot be solved", Toast.LENGTH_SHORT).show()
            return false
        }

        // Update the UI with the solved puzzle
        index = 0
        for (row in 0..8) {
            for (col in 0..8) {
                val cell = gridLayout.getChildAt(index) as EditText
                cell.setText(sudoku[row][col].toString())
                index++
            }
        }

        Toast.makeText(this, "Puzzle solved", Toast.LENGTH_SHORT).show()
        return true
    }


    private fun clearGrid() {
        // Clear the values in the grid
        for (i in 0 until gridLayout.childCount) {
            val cell = gridLayout.getChildAt(i) as EditText
            cell.setText("")
        }
    }
}
