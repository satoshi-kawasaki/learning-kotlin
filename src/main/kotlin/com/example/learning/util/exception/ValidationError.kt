package com.example.learning.util.exception

/**
 * ドメインオブジェクトのバリデーションにおけるエラー型
 */
interface ValidationError {
    /**
     * エラーメッセージ
     */
    val message: String
}
