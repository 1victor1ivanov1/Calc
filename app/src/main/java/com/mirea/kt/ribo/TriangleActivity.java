package com.mirea.kt.ribo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class TriangleActivity extends AppCompatActivity {

    EditText inputSideA, inputSideB, inputSideC;
    TextView outputPerimeter, outputArea;
    double calculatedPerimeter = 0, calculatedArea = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);

        // Привязка элементов интерфейса
        inputSideA = findViewById(R.id.input_side_a);
        inputSideB = findViewById(R.id.input_side_b);
        inputSideC = findViewById(R.id.input_side_c);
        outputPerimeter = findViewById(R.id.output_perimeter);
        outputArea = findViewById(R.id.output_area);

        // Обработчик нажатия на кнопку "Рассчитать"
        findViewById(R.id.btn_calculate).setOnClickListener(v -> {
            AppUtils.logInfo("Начало расчета для квадрата");

            calculate();
        });

        // Слушатель для кнопки "Поделиться результатами"
        findViewById(R.id.btn_share_results).setOnClickListener(v -> {
            AppUtils.logInfo("Отправка результатов рассчета для треугольника");

            AppUtils.ShareResult result = AppUtils.shareResults(AppUtils.GeoType.Triangle, calculatedPerimeter, calculatedArea);

            if (result.ok) {
                // Intent для обмена
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result.message);
                sendIntent.setType("text/plain"); // Тип передаваемых данных — обычный текст

                // Выбор приложения для отправки
                Intent chooser = Intent.createChooser(sendIntent, "Отправить результаты через...");
                startActivity(chooser);

                AppUtils.logInfo("Результаты рассчетов для треугольника отправлены успешно");
            } else {
                outputPerimeter.setText(result.error);
                outputArea.setText("");

                AppUtils.logErr("Ошибка при отправке результатов рассчета для треугольника: " + result.error);
            }
        });
    }

    private void calculate() {
        try {
            double triSideA = Double.parseDouble(inputSideA.getText().toString());
            double triSideB = Double.parseDouble(inputSideB.getText().toString());
            double triSideC = Double.parseDouble(inputSideC.getText().toString());

            AppUtils.checkPositiveValue(triSideA, "Сторону А треугольника нельзя задать равной или меньшей нуля.");
            AppUtils.checkPositiveValue(triSideB, "Сторону B треугольника нельзя задать равной или меньшей нуля.");
            AppUtils.checkPositiveValue(triSideC, "Сторону C треугольника нельзя задать равной или меньшей нуля.");

            AppUtils.checkIfTrianglePossible(triSideA, triSideB, triSideC);

            calculatedPerimeter = triSideA + triSideB + triSideC;
            double pSemi = calculatedPerimeter / 2;
            calculatedArea = Math.sqrt(pSemi * (pSemi - triSideA) * (pSemi - triSideB) * (pSemi - triSideC));

            // Вывод результатов
            outputPerimeter.setText("Периметр: " + String.format("%.5f", calculatedPerimeter));
            outputArea.setText("Площадь: " + String.format("%.5f", calculatedArea));

            AppUtils.logInfo("Завершение расчета успешно. Площадь: " + calculatedArea + ", периметер: " + calculatedPerimeter);
        } catch (NumberFormatException e) {
            // Вывод ошибки
            outputPerimeter.setText("Ошибка: проверьте введенные данные");
            outputArea.setText("");

            AppUtils.logErr("Ошибка парсинга числового значения: " + e.getMessage());
        }
    }
}