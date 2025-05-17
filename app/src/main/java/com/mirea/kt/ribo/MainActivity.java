package com.mirea.kt.ribo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUtils.logInfo("Запуск приложения 'Геометрический калькулятор (Calc)'");

        // Слушатель для кнопки "Об авторе"
        findViewById(R.id.btn_about).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран об авторе");

            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        });

        // Слушатель для кнопки "квадрат"
        findViewById(R.id.btn_square).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран рассчета для квадрата");

            startActivity(new Intent(MainActivity.this, SquareActivity.class));
        });

        // Слушатель для кнопки "прямоугольник"
        findViewById(R.id.btn_rectangle).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран рассчета для прямоугольника");

            startActivity(new Intent(MainActivity.this, RectangleActivity.class));
        });

        // Слушатель для кнопки "круг"
        findViewById(R.id.btn_circle).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран рассчета для круга");

            startActivity(new Intent(MainActivity.this, CircleActivity.class));
        });

        // Слушатель для кнопки "треугольник"
        findViewById(R.id.btn_triangle).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран рассчета для треугольника");

            startActivity(new Intent(MainActivity.this, TriangleActivity.class));
        });

        // Слушатель для кнопки "ромб"
        findViewById(R.id.btn_rhombus).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран рассчета для ромба");

            startActivity(new Intent(MainActivity.this, RhombusActivity.class));
        });

        // Слушатель для кнопки "трапеция"
        findViewById(R.id.btn_trapezoid).setOnClickListener(v -> {
            AppUtils.logInfo("Переход на экран рассчета для трапеции");

            startActivity(new Intent(MainActivity.this, TrapezoidActivity.class));
        });
    }
}