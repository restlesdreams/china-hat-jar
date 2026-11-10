@echo off
chcp 65001 >nul
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle не найден. Установите Gradle 8.6 и добавьте его в PATH.
  pause
  exit /b 1
)
gradle build
if errorlevel 1 (
  echo Сборка завершилась с ошибкой.
  pause
  exit /b 1
)
echo Готово: build\libs\china-hat-1.0.0.jar
pause
