package com.mirea.kt.ribo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class TrapezoidActivity extends AppCompatActivity {

    EditText inputUpperBase, inputLowerBase, inputHeight;
    TextView outputPerimeter, outputArea;
    double calculatedPerimeter = 0, calculatedArea = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trapezoid);

        // Привязка элементов интерфейса
        inputUpperBase = findViewById(R.id.input_upper_base);
        inputLowerBase = findViewById(R.id.input_lower_base);
        inputHeight = findViewById(R.id.input_height);
        outputPerimeter = findViewById(R.id.output_perimeter);
        outputArea = findViewById(R.id.output_area);

        // Обработчик нажатия на кнопку "Рассчитать"
        findViewById(R.id.btn_calculate).setOnClickListener(v -> {
            AppUtils.logInfo("Начало расчета для трапеции");

            calculate();
        });

        // Слушатель для кнопки "Поделиться результатами"
        findViewById(R.id.btn_share_results).setOnClickListener(v -> {
            AppUtils.logInfo("Отправка результатов рассчета для трапеции");

            AppUtils.ShareResult result = AppUtils.shareResults(AppUtils.GeoType.Trapezoid, calculatedPerimeter, calculatedArea);

            if (result.ok) {
                // Intent для обмена
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result.message);
                sendIntent.setType("text/plain"); // Тип передаваемых данных — обычный текст

                // Выбор приложения для отправки
                Intent chooser = Intent.createChooser(sendIntent, "Отправить результаты через...");
                startActivity(chooser);

                AppUtils.logInfo("Результаты рассчетов для трапеции отправлены успешно");
            } else {
                outputPerimeter.setText(result.error);
                outputArea.setText("");

                AppUtils.logErr("Ошибка при отправке результатов рассчета для трапеции: " + result.error);
            }
        });
    }

    private void calculate() {
        try {
            double upperBase = Double.parseDouble(inputUpperBase.getText().toString());
            double lowerBase = Double.parseDouble(inputLowerBase.getText().toString());
            double height = Double.parseDouble(inputHeight.getText().toString());

            AppUtils.checkPositiveValue(upperBase, "Верхнюю основу трапеции нельзя задать равной или меньшей нуля.");
            AppUtils.checkPositiveValue(lowerBase, "Нижнюю основу трапеции нельзя задать равной или меньшей нуля.");
            AppUtils.checkPositiveValue(height, "Высоту трапеции нельзя задать равной или меньшей нуля.");

            calculatedPerimeter = upperBase + lowerBase + 2 * Math.sqrt(((lowerBase - upperBase) / 2) * ((lowerBase - upperBase) / 2) + height * height);
            calculatedArea = ((upperBase + lowerBase) / 2) * height;

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