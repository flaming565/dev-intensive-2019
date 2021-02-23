package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val validationMessage = ValidationMessage.valueOf(question.name).validate(answer)
        if (validationMessage.isEmpty()) {
            return if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - это правильный ответ!\n${question.question}" to status.color
            } else {
                status = status.nextStatus()
                "Это не правильный ответ!\n" +
                        "${question.question}" to status.color
            }
        } else
            return "$validationMessage\n${question.question}" to status.color
    }

    enum class ValidationMessage(val message: String) {
        NAME("Имя должно начинаться с заглавной буквы") {
            override fun validate(answer: String): String {
                return if (answer.isNotEmpty() && answer[0].isUpperCase())
                    ""
                else
                    message
            }
        },
        PROFESSION("Профессия должна начинаться со строчной буквы") {
            override fun validate(answer: String): String {
                return if (answer.isNotEmpty() && answer[0].isLowerCase())
                    ""
                else
                    message
            }
        },
        MATERIAL("Материал не должен содержать цифр") {
            override fun validate(answer: String): String {
                return if (answer.none { it.isDigit() })
                    ""
                else
                    message
            }
        },
        BDAY("Год моего рождения должен содержать только цифры") {
            override fun validate(answer: String): String {
                return if (answer.none { !it.isDigit() })
                    ""
                else
                    message
            }
        },
        SERIAL("Серийный номер содержит только цифры, и их 7") {
            override fun validate(answer: String): String {
                return if (answer.length == 7 && answer.none { !it.isDigit() })
                    ""
                else
                    message
            }
        },
        IDLE("") {
            override fun validate(answer: String): String {
                return ""
            }
        };

        abstract fun validate(answer: String): String
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 255, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex)
                values()[this.ordinal + 1]
            else
                values()[0]
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }
}