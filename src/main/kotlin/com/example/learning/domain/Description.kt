package com.example.learning.domain

import arrow.core.EitherNel
import arrow.core.leftNel
import arrow.core.right
import com.example.learning.util.exception.ValidationError

/**
 * 「作成済記事の概要」の Value Object
 */
interface Description {
    /**
     * 概要
     */
    val value: String

    /**
     * Validation 有り、作成済記事の概要
     *
     * @property value
     */
    private data class ValidatedDescription(
        override val value: String,
    ) : Description

    /**
     * Validation 無し、作成済記事の概要
     *
     * @property value
     */
    private data class DescriptionWithoutValidation(
        override val value: String,
    ) : Description

    /**
     * Factory メソッド
     *
     * @constructor Create empty Companion
     */
    companion object {
        private const val MAX_LENGTH: Int = 64

        /**
         * Validation 有り
         *
         * @param description
         * @return
         */
        fun new(description: String): EitherNel<CreationError, Description> {
            /**
             * 文字数チェック
             *
             * 最大文字数より長かったら、早期リターン
             */
            if (description.length > MAX_LENGTH) {
                return CreationError.TooLong(MAX_LENGTH).leftNel()
            }

            return ValidatedDescription(description).right()
        }

        /**
         * Validation 無し
         *
         * @param description
         * @return
         */
        fun newWithoutValidation(description: String): Description = DescriptionWithoutValidation(description)
    }

    /**
     * オブジェクト生成時のドメインルール
     *
     */
    sealed interface CreationError : ValidationError {
        /**
         * 文字数制限
         *
         * 長すぎては駄目
         *
         * @property maximum
         */
        data class TooLong(
            val maximum: Int,
        ) : CreationError {
            override val message: String
                get() = "description は $maximum 文字以下にしてください"
        }
    }
}
