# TelegramBot
Telegram bot for checking the statistics of SPBHL-hockey players

====================================================================

Приложение анализирует текущие показатели интересующих игроков Санкт-Петербургской хоккейной лиги и оповещает пользователя в чате Telegram(бот **@score_guys**) в случае если игрок забросил шайбу, отдал голевую передачу или заработал штраф.
Все актуальные данные по играм лиги СПБХЛ берутся с сайта https://spbhl.ru.
Приложение задеплоено на Heroku и каждые 10 минут проверяет сайт СПБХЛ на наличие изменений в статистике интересующих игроков.  

Пример страницы с текущей статистикой игрока: https://spbhl.ru/Player?PlayerID=5eaec1e7-eb0e-4838-8151-44cf4a6e96a5

Бот **@score_guys** поддерживает следующие команды:
- /add_{id игрока на сайте SPBHL} - *добавляет игрока в базу интересующих хоккеистов*
- /delete_{имя и фамилия игрока} - *удаляет игрока из базы и перестает отслеживать его статистику*
- /getAll - *выводит список всех отслеживаемых хоккеистов*

Например, мы хотим отслеживать статистику братьев Максима и Александра Тюшковых.  

Добавляем их в базу (id берем с сайта СПБХЛ):  
![](https://downloader.disk.yandex.ru/preview/8f42be811bfae8514ed42198c176eb0fe471322a1b82b5d2ae5eb38d9fef6602/60a5b9e8/rUMg5EvJQG9BkU51jTsu7aZJaGOabTyJyaP2BYn-hUPle8rgLFBTyviXWg3nbzRWm8T0x0XvEi7f-4DigTPFFg%3D%3D?uid=0&filename=Group%202.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=400x400)​  

Можем посмотреть список отслеживаемых игроков:  
![](https://downloader.disk.yandex.ru/preview/e3e8d26068561cf90115f369852b9c7c325e0dd19546abd12f970e8da3c15689/60a5c2fb/WqvIvb8X97PchJ7ib_jXgyLUkk3lfiorFMmi35BpZbREQpCKUdq0098CYsw28JI-G_QsTzGYNAchvya6Krvs2Q%3D%3D?uid=0&filename=Group%201.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=375x375)​  

Допустим, в последнем хоккейном матче Максим Тюшков забил гол, отдал голевую передачу и заработал штраф. Мы получим оповещение не позднее 10 минут после того, как свежая информация появится на сайте:  
![](https://downloader.disk.yandex.ru/preview/8d80f1da72ea2cb6dd342d9c4bc395c147fdcc13390ffa910c714158d4d1e294/60a5c4a5/id-0dnCSebIkbzLuupzFDl9_ZJZwnt0ewFtrVQ4F8IbOxY_vxG50hcLaUmWpYDLp516gb851AE0vD1IBuHqSNQ%3D%3D?uid=0&filename=Group%204.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=375x375)​  

Если вдруг статистика хоккеиста перестала интересовать, то его можно удалить:  
![](https://downloader.disk.yandex.ru/preview/04771d8e25170af854b84230e6765eb62f22463923341912b964245a26a595f3/60a5c562/kIUJvyKcmzDyERGFw9GphqZJaGOabTyJyaP2BYn-hUNaGVkQvoG8pyY4Mf5tfqwcn2tsgsQ3O-Kx0nAlYxn_0Q%3D%3D?uid=0&filename=Group%203.png&disposition=inline&hash=&limit=0&content_type=image%2Fpng&owner_uid=0&tknv=v2&size=375x375)​

