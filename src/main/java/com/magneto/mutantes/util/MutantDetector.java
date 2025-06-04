package com.magneto.mutantes.util;

public class MutantDetector {

    /**
     * Define las direcciones posibles para buscar secuencias. Cada array
     * interno representa [delta_fila, delta_columna].
     */
    private static final int[][] DIRECTIONS = {
        {0, 1}, // Horizontal (horizontal)
        {1, 0}, // Vertical (vertical)
        {1, 1}, // Diagonal principal
        {1, -1} // Diagonal secundaria
    };

    /**
     * Detecta si un humano es mutante según su secuencia de ADN. Un humano es
     * mutante si existen más de una secuencia de cuatro letras iguales de forma
     * horizontal, vertical u oblicua.
     *
     * @param dna Un arreglo de Strings que representa cada fila de una matriz
     * NxN de ADN. Las letras solo pueden ser 'A', 'T', 'C', 'G'.
     * @return true si es mutante, false en caso contrario.
     */
    public boolean isMutant(String[] dna) {
        if (!isValidInput(dna)) {
            return false;
        }
        char[][] dnaMatrix = toMatrix(dna);
        return hasMoreThanOneMutantSequence(dnaMatrix);
    }

    /**
     * Valida la entrada: no nula, matriz NxN y solo caracteres válidos.
     */
    private boolean isValidInput(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }
        int n = dna.length;
        for (String row : dna) {
            if (row.length() != n) {
                return false;
            }
            for (char c : row.toCharArray()) {
                if (!isValidChar(c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Convierte el arreglo de Strings a una matriz de caracteres.
     */
    private char[][] toMatrix(String[] dna) {
        int n = dna.length;
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        return matrix;
    }

    /**
     * Busca más de una secuencia mutante en la matriz.
     */
    private boolean hasMoreThanOneMutantSequence(char[][] dnaMatrix) {
        int n = dnaMatrix.length;
        int mutantSequencesFound = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (isMutantSequenceStart(dnaMatrix, row, col)) {
                    mutantSequencesFound++;
                    if (mutantSequencesFound > 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica si desde la posición dada parte una secuencia mutante en
     * cualquier dirección.
     */
    private boolean isMutantSequenceStart(char[][] matrix, int row, int col) {
        for (int[] direction : DIRECTIONS) {
            if (checkSequence(matrix, row, col, direction[0], direction[1])) {
                return true;
            }
        }
        return false;
    }

    /* public boolean isMutant(String[] dna) {
        // 1. Validación básica de entrada
        if (dna == null || dna.length == 0) {
            return false;
        }

        int n = dna.length;
        char[][] dnaMatrix = new char[n][n];

        // 2. Validar dimensiones y caracteres, y convertir a char[][]
        for (int i = 0; i < n; i++) {
            String row = dna[i];
            if (row.length() != n) {
                // No es una matriz NxN
                return false;
            }
            for (int j = 0; j < n; j++) {
                char c = row.charAt(j);
                if (!isValidChar(c)) {
                    // Contiene caracteres inválidos
                    return false;
                }
                dnaMatrix[i][j] = c;
            }
        }

        // 3. Contar secuencias mutantes
        int mutantSequencesFound = 0;
        // Recorrer cada celda de la matriz de ADN
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Para cada celda, revisar las cuatro direcciones posibles
                for (int[] direction : DIRECTIONS) {
                    int rowDir = direction[0];
                    int colDir = direction[1];

                    // Verificar si existe una secuencia de 4 caracteres desde (row, col) en la dirección dada
                    if (checkSequence(dnaMatrix, row, col, rowDir, colDir)) {
                        mutantSequencesFound++;
                        // Salida temprana si se encuentran más de una secuencia
                        if (mutantSequencesFound > 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false; // Menos de dos secuencias mutantes encontradas
    } */
    /**
     * Verifica si existe una secuencia de 4 caracteres idénticos desde
     * (startRow, startCol) en la dirección dada.
     *
     * @param matrix Matriz de ADN.
     * @param startRow Fila inicial.
     * @param startCol Columna inicial.
     * @param rowDir Incremento de fila (-1, 0 o 1).
     * @param colDir Incremento de columna (-1, 0 o 1).
     * @return true si encuentra una secuencia de 4 caracteres iguales, false en
     * caso contrario.
     */
    private boolean checkSequence(char[][] matrix, int startRow, int startCol, int rowDir, int colDir) {
        int n = matrix.length; // Tamaño de la matriz NxN

        // Verifica que la posición inicial permita una secuencia de 4 caracteres en la dirección dada
        // Esto es crucial para evitar excepciones ArrayIndexOutOfBounds
        if (startRow + 3 * rowDir < 0 || startRow + 3 * rowDir >= n
                || startCol + 3 * colDir < 0 || startCol + 3 * colDir >= n) {
            return false; // No hay espacio suficiente para una secuencia de 4 caracteres
        }

        char baseChar = matrix[startRow][startCol];

        // Verifica los siguientes 3 caracteres en la dirección especificada
        for (int k = 1; k < 4; k++) {
            int currentRow = startRow + rowDir * k;
            int currentCol = startCol + colDir * k;

            // Verifica si el caracter coincide con el caracter base
            if (matrix[currentRow][currentCol] != baseChar) {
                return false; // No es una secuencia
            }
        }
        return true; // Todos los caracteres coinciden
    }

    /**
     * Valida si un caracter es una base de ADN permitida.
     *
     * @param c Caracter a validar.
     * @return true si es 'A', 'T', 'C' o 'G', false en caso contrario.
     */
    private boolean isValidChar(char c) {
        return c == 'A' || c == 'T' || c == 'C' || c == 'G';
    }
}
