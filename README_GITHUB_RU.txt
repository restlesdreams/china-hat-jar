CHINA HAT — сборка JAR через GitHub Actions

1. Создайте аккаунт на https://github.com/ (если его ещё нет).
2. На GitHub нажмите + -> New repository.
3. Назовите репозиторий china-hat, выберите Public или Private и нажмите Create repository.
4. На странице пустого репозитория нажмите Add file -> Upload files.
5. Откройте распакованную папку china-hat на компьютере и перетащите ВСЁ ЕЁ СОДЕРЖИМОЕ в окно загрузки GitHub.
   Важно: вместе с файлами должна загрузиться скрытая папка .github/workflows/build.yml.
   Если браузер не загружает скрытую папку, включите показ скрытых элементов в Проводнике или используйте GitHub Desktop.
6. Внизу страницы нажмите Commit changes.
7. Откройте вкладку Actions репозитория.
8. Выберите workflow Build China Hat слева. Если он ещё не запустился, нажмите Run workflow -> Run workflow.
9. Дождитесь зелёной галочки у запуска сборки.
10. Откройте завершённый запуск. Внизу страницы в разделе Artifacts скачайте China-Hat-Fabric-1.20.1.zip.
11. Распакуйте скачанный архив. Внутри будет china-hat-1.0.0.jar.
12. Положите JAR в %appdata%\.minecraft\mods. Установите Fabric Loader 1.20.1 и Fabric API 1.20.1.

Если сборка красная (Failed), откройте запуск Actions, раскройте шаг Build mod и пришлите скриншот или текст ошибки.
