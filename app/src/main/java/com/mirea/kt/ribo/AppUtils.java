package com.mirea.kt.ribo;

import android.util.Log;

public class AppUtils {

    private static final String TAG = "Calc"; // Имя журнала для логов

    public static class ShareResult {
        public boolean ok;
        public String message;
        public String error;
    }

    public static enum GeoType {
        None,
        Square,
        Rectangle,
        Circle,
        Triangle,
        Rhombus,
        Trapezoid,
    }

    public static void logInfo(String msg) {
        Log.d(TAG, msg);
    }

    public static void logErr(String msg) {
        Log.e(TAG, msg);
    }

    // Функция для проверки значений
    public static void checkPositiveValue(double value, String errorMsg) throws NumberFormatException {
        if (value <= 0) {
            throw new NumberFormatException(errorMsg);
        }
    }

    // Метод для отправки результатов
    public static ShareResult shareResults(GeoType type, double perimeter, double area) {
        ShareResult result = new ShareResult();

        // Не выбрана фигура
        if (type == GeoType.None) {
            logInfo("Ошибка использования функционала 'поделиться результатом': фигура не выбрана ");

            result.ok = false;
            result.message = "";
            result.error = "Ошибка: перед тем как поделиться выберите фигуру";
            return result;
        }

        // Не введены данные фигуры
        if (perimeter <= 0 || area <= 0) {
            logErr("Ошибка использования функционала 'поделиться результатом': данные о фигуре не введены ");

            result.ok = false;
            result.message = "";
            result.error = "Ошибка: перед тем как поделиться введите данные фигуры";
            return result;
        }

        // Текст для обмена результатами
        result.ok = true;
        result.message = "✅ Геометрический калькулятор\n" +
                "Результат для фигуры: " + GeoTypeToString(type) + "\n" +
                "Периметр: " + String.format("%.5f", perimeter) + "\n" +
                "Площадь: " + String.format("%.5f", area);
        result.error = "";
        return result;
    }

    // Метод для преобразования типа к строке
    public static String GeoTypeToString(GeoType type) {
        //Преобразование типа фигуры к строке для вывода через сообщения через стороние API
        switch (type) {
            case Square:
                return "Квадрат";
            case Rectangle:
                return "Прямоугольник";
            case Circle:
                return "Круг";
            case Triangle:
                return "Треугольник";
            case Rhombus:
                return "Ромб";
            case Trapezoid:
                return "Трапеция";
        }

        return "";
    }

    // Метод для проверки возможности существования треугоника
    public static void checkIfTrianglePossible(double a, double b, double c) {
        //Проверка на то что треугольник возможен
        boolean check1 = (a + b) > c;
        boolean check2 = (a + c) > b;
        boolean check3 = (b + c) > a;

        if (!check1 || !check2 || !check3) {
            throw new NumberFormatException("Сумма любых двух сторон треугольника всегда больше третей стороны");
        }
    }
}
