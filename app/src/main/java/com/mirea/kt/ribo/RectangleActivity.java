package com.mirea.kt.ribo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RectangleActivity extends AppCompatActivity {

    EditText inputWidth, inputHeight;
    TextView outputPerimeter, outputArea;
    double calculatedPerimeter = 0, calculatedArea = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rectangle);

        // Привязка элементов интерфейса
        inputWidth = findViewById(R.id.input_width);
        inputHeight = findViewById(R.id.input_height);
        outputPerimeter = findViewById(R.id.output_perimeter);
        outputArea = findViewById(R.id.output_area);

        // Обработчик нажатия на кнопку "Рассчитать"
        findViewById(R.id.btn_calculate).setOnClickListener(v -> {
            AppUtils.logInfo("Начало расчета для прямоугольника");

            calculate();
        });

        // Слушатель для кнопки "Поделиться результатами"
        findViewById(R.id.btn_share_results).setOnClickListener(v -> {
            AppUtils.logInfo("Отправка результатов рассчета для прямоугольника");

            AppUtils.ShareResult result = AppUtils.shareResults(AppUtils.GeoType.Rectangle, calculatedPerimeter, calculatedArea);

            if (result.ok) {
                // Intent для обмена
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, result.message);
                sendIntent.setType("text/plain"); // Тип передаваемых данных — обычный текст

                // Выбор приложения для отправки
                Intent chooser = Intent.createChooser(sendIntent, "Отправить результаты через...");
                startActivity(chooser);

                AppUtils.logInfo("Результаты рассчетов для прямоугольника отправлены успешно");
            } else {
                outputPerimeter.setText(result.error);
                outputArea.setText("");

                AppUtils.logErr("Ошибка при отправке результатов рассчета для прямоугольника: " + result.error);
            }
        });
    }

    private void calculate() {
        try {
            double rectWidth = Double.parseDouble(inputWidth.getText().toString());
            double rectHeight = Double.parseDouble(inputHeight.getText().toString());

            AppUtils.checkPositiveValue(rectWidth, "Ширину прямоугольника нельзя задать равной или меньшей нуля.");
            AppUtils.checkPositiveValue(rectHeight, "Высоту прямоугольника нельзя задать равной или меньшей нуля.");

            calculatedPerimeter = 2 * (rectWidth + rectHeight);
            calculatedArea = rectWidth * rectHeight;

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