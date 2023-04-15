package com.example.financieraoh.commons

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class UtilDate {
    companion object {

        private fun dateFormat(dateString: String?, pattern: String = "yyyy-MM-dd'T'HH:mm:ss"): Date? {
            val simpleDateFormat = SimpleDateFormat(pattern)
            return simpleDateFormat.parse(dateString)
        }

        fun timeFormat(dateString: String?): String? {
            val date = dateFormat(dateString)
            val format = SimpleDateFormat("HH:mm")
            return format.format(date)
        }

        fun dateFormatDaily(dateString: String?): String {
            val transactionDate = dateFormat(dateString, "yyyy-MM-dd")
            val patternFechaAntes5 = "E"
            val patternFechaDespues5 = "dd MMM yyyy"
            var pattern = ""

            val hoy = Date()
            val fechaComparar = SimpleDateFormat("dd-MM-yyyy")

            val tomorrow = Calendar.getInstance(Locale("es", "EC"))
            tomorrow.time = hoy
            tomorrow.add(Calendar.DAY_OF_YEAR, 1)

            var bandReemplazaDia = false
            var tiempo = "Hoy"

            if (fechaComparar.parse(fechaComparar.format(transactionDate)).equals(
                    fechaComparar.parse(
                        fechaComparar.format(
                            hoy
                        )
                    )
                )) {
                pattern = patternFechaAntes5
                bandReemplazaDia = true
                tiempo = "Hoy"
            } else if (fechaComparar.parse(fechaComparar.format(transactionDate)).equals(
                    fechaComparar.parse(
                        fechaComparar.format(
                            tomorrow.time
                        )
                    )
                )) {
                pattern = patternFechaAntes5
                bandReemplazaDia = true
                tiempo = "Mañana"
            } else {
                tomorrow.add(Calendar.DAY_OF_YEAR, 5)
                if (fechaComparar.parse(fechaComparar.format(transactionDate)) <= fechaComparar.parse(
                        fechaComparar.format(
                            tomorrow.time
                        )
                    ))
                    pattern = patternFechaAntes5
                else
                    pattern = patternFechaDespues5
            }

            val simpleDateFormat = SimpleDateFormat(pattern)
            val capitalDays = arrayOf(
                "",
                "Domingo",
                "Lunes",
                "Martes",
                "Miércoles",
                "Jueves",
                "Viernes",
                "Sábado"
            )
            val capitalMonths = arrayOf(
                "ene.",
                "feb.",
                "mar.",
                "abr.",
                "may.",
                "jun.",
                "jul.",
                "ago.",
                "sep.",
                "oct.",
                "nov.",
                "dic."
            )

            val dateFormatSymbols = DateFormatSymbols(Locale("es", "EC"))
            dateFormatSymbols.shortWeekdays = capitalDays
            dateFormatSymbols.shortMonths = capitalMonths
            simpleDateFormat.dateFormatSymbols = dateFormatSymbols

            var formateado = simpleDateFormat.format(transactionDate).toString()

            if (bandReemplazaDia) {
                formateado = tiempo
            }

            return formateado
        }
    }
}