# Hodujjajko
Aplikacja Hoduj jajko służy do monitorowania aktywności. Aby aplikacja działa w pełni wymagane jest włączenie internetu
oraz czujnika GPS. W przypadku słabego zasięgu niektóre funkcje mogą nie działać poprawnie. Dodatkowo wymagane są sensory
GRAVITY oraz STEP COUNTER jednakże bez nich aplikacja działa poprawnie nie włączając niektórych funkcji. Aplikacja mierzy naszą aktywność
na 3 różne sposoby:
<ul>
<li>Praca własna - jest to timer z możliwością ustawiania interwałów czasowych ( wykorzystana jest tu technika nauki Pomodoro)
<li> Bieganie - na podstawie GPS mierzona jest odległość jaką użytkownik przeszedł/przebiegł. Po zakończonej aktywności użytkownik
może zobaczyć na mapie swoją trasę.
<li> Krokomierz - pozwala mierzyć ilość kroków jaką przeszedł użytkownik (wymagany sensor STEP COUNTER).
</ul>
<br>
Po każdej aktywności użytkownik otrzymuje punkty i w zależności od ich ilości wyświetla mu się rodzaj kurczaczka.
<br>
<br>
Dodatkowo użytkownik może sprawdzić jakie aktywności wykonywał wciskając przycisk "Twoja aktywność". Jest to menu kontekstowe,
gdzie wypisane są wszystkie aktywności, moment ich rozpoczęcia, punkty oraz osiągnięty wynik. Użytkownik ma również możliwość usuwania 
aktywności poprzez dłuższe kliknięcie na wybraną aktywność.
<br> 
<br>
Istnieje również możliwość planowania swoich aktywności. Służy do tego grafik. Można dodawać nową aktywność do grafiku. Ustawia się tam
nazwę, typ aktywności - jednorazowa, co tygodniowa, czas startu, czas zakończenia. Jeśli użytkownik zechce, aby aplikacja przypomniała
o aktywności 5 minut przed jej zakończeniem, może kliknąć w datę na kalendarzu znajdującym się w zakładce grafik i ustawić powiadomienie.
5 minut przed użytkownik otrzyma informacje o swojej aktywności poprzez wyświetlenie się okienka z nazwą aktywności i temperaturą na zewnątrz
(wykorzystany serwer pogodowy) oraz poprzez sygnał dzwiękowy, który wyłącza się po wciśnięciu przycisku lub podniesieniu telefonu 
(wymagany sensor GRAVITY). 

<h3> Instalacja
<br>
Aby zainstalować aplikację należy pobrać pliki z GitHuba, podpiąć telefon do komputera i uruchomić projekt (poprzez np. Android Studio)
wybierając jako urządzenie podpięty telefon.
</h3>
