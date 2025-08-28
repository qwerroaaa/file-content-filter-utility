# file-content-filter-utility

<h1>Инструкция по запуску</h1>
<p>Основная команда для запуска: java -jar util.jar [опции запуска] [входные файлы]</p>
<p>Опции:</p>
<ul>
  <li><code>-p</code>, <code>--prefix</code> — добавление префикса к выходным файлам</li>
  <li><code>-a</code>, <code>--append</code> — режим добавления в существующие файлы</li>
  <li><code>-s</code>, <code>--shortStat</code> — вывод краткой статистики</li>
  <li><code>-f</code>, <code>--fullStat</code> — вывод полной статистики</li>
  <li><code>-o</code>, <code>--out</code> — путь для сохранения результатов</li>
</ul>

<p>Пример запуска: <code>java -jar util.jar -s -a -o output input/in1.txt input/in2.txt</p></code>

<h1>Особенности реализации</h1>
<ul>
  <li>Версия Java - 24 (но код писался под 8 Java)</li>
  <li>Система сборки и её версия - Maven 3.9.11</li>
  <li>Сторонние библиотеки - Apache Common CLI 1.10.0 <code>(ссылка: <a href = "https://mvnrepository.com/artifact/commons-cli/commons-cli/1.10.0">https://mvnrepository.com/artifact/commons-cli/commons-cli/1.10.0)</code></li>
</ul>
<h3>Небольшие уточнения</h3>
<p>Для ввода значений с E используйте английскую раскладку - <code>1.23E12, 123E+132, 1.2E-2</code></p>
<p>Пустая строка считается стройкой, поэтому записывается в string.txt</p>
<p>Входные файлы находятся в папке <b>input</b></p>
