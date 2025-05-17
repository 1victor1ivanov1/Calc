package com.mirea.kt.ribo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RhombusActivity extends AppCompatActivity {

    EditText inputDiagonalA, inputDiagonalB;
    TextView outputPerimeter, outputArea;
    double calculatedPerimeter = 0, calculatedArea = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhombus);

        // Привязка элементов интерфейса
        inputDiagonalA = findViewById(R.id.input_diagonal_a);
        inputDiagonalB = findViewById(R.id.input_diagonal_b);
        outputPerimeter = findViewById(R.id.output_perimeter);
        outputArea = findViewById(R.id.output_area);

        // Обработчик нажатия на кнопку "Рассчитать"
        findViewById(R.id.btn_calculate).setOnClickListener(v -> {
            AppUtils.logInfo("Начало расчета для ромба");

            calculate();
        });

        // Слушатель для кнопки "Поделиться результатами"
        findViewById(R.id.btn_share_results).setOnClickListener(v -> {
            AppUtils.logInfo("Отправка результатов рассчета для ромба");

            AppUtils.ShareResult result = AppUtils.shareResults(AppUtils.GeoType.Rhombus, calculatedPerimeter, calculatedArea);

            if (result.ok) {
                // Intent для обмена
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result.message);
                sendIntent.setType("text/plain"); // Тип передаваемых данных — обычный текст

                // Выбор приложения для отправки
                Intent chooser = Intent.createChooser(sendIntent, "Отправить результаты через...");
                startActivity(chooser);

                AppUtils.logInfo("Результаты рассчетов для ромба отправлены успешно");
            } else {
                outputPerimeter.setText(result.error);
                outputArea.setText("");

                AppUtils.logErr("Ошибка при отправке результатов рассчета для ромба: " + result.error);
            }
        });
    }

    private void calculate() {
        try {
            double diag1 = Double.parseDouble(inputDiagonalA.getText().toString());
            double diag2 = Double.parseDouble(inputDiagonalB.getText().toString());

            AppUtils.checkPositiveValue(diag1, "Первая диагональ ромба должна быть положительной.");
            AppUtils.checkPositiveValue(diag2, "Вторая диагональ ромба должна быть положительной.");

            calculatedPerimeter = 2 * Math.sqrt(diag1 * diag1 + diag2 * diag2);
            calculatedArea = (diag1 * diag2) / 2;

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