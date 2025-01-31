import java.util.*;
import java.util.stream.Collectors;

interface LibraryItem {
    String getTitle();
    String getAuthor();
    String displayInfo();
}

class Book implements LibraryItem {
    private final String author;
    private final String title;
    private final String genre;
    private final int pages;

    public Book(String author, String title, String genre, int pages) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.pages = pages;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getAuthor() { return author; }

    @Override
    public String displayInfo() {
        return String.format("Книга: '%s' (автор: %s, жанр: %s, %d стор.)", 
               title, author, genre, pages);
    }
}

class Newspaper implements LibraryItem {
    private final String title;
    private final String date;
    private final List<String> headlines;

    public Newspaper(String title, String date, List<String> headlines) {
        this.title = title;
        this.date = date;
        this.headlines = headlines;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getAuthor() { return null; }

    @Override
    public String displayInfo() {
        return String.format("Газета: '%s' (дата: %s, заголовки: %s)", 
               title, date, String.join(", ", headlines));
    }
}

class Almanac implements LibraryItem {
    private final String title;
    private final List<String> works;

    public Almanac(String title, List<String> works) {
        this.title = title;
        this.works = works;
    }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getAuthor() { return null; }

    @Override
    public String displayInfo() {
        return String.format("Альманах: '%s' (твори: %s)", 
               title, String.join(", ", works));
    }
}

class Catalog {
    private final List<LibraryItem> items = new ArrayList<>();
    private final Random random = new Random();

    public void testInitialization() {
        items.add(new Book("Дж. Роулінг", "Гаррі Поттер і філософський камінь", 
                "Фентезі", 223));
        items.add(new Newspaper("The Guardian", "2023-10-05", 
                Arrays.asList("Саміт з клімату", "Нові технології")));
        items.add(new Almanac("Найкраща наукова фантастика 2023", 
                Arrays.asList("Дюна Френка Герберта", "Нейромант Вільяма Гібсона")));
    }

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void addRandomItem() {
        switch (random.nextInt(3)) {
            case 0 -> items.add(createRandomBook());
            case 1 -> items.add(createRandomNewspaper());
            case 2 -> items.add(createRandomAlmanac());
        }
    }

    private Book createRandomBook() {
        List<String> authors = Arrays.asList(
            "Джордж Орвелл", "Агата Крісті", "Дж.Р.Р. Толкін"
        );
        List<String> titles = Arrays.asList(
            "1984", "Вбивство у східному експресі", "Гобіт"
        );
        List<String> genres = Arrays.asList(
            "Антиутопія", "Детектив", "Фентезі"
        );
        
        return new Book(
            randomElement(authors),
            randomElement(titles),
            randomElement(genres),
            100 + random.nextInt(401)
        );
    }

    private Newspaper createRandomNewspaper() {
        List<String> titles = Arrays.asList(
            "The Times", "Daily News", "Morning Post"
        );
        List<String> dates = Arrays.asList(
            "2023-01-15", "2023-06-20", "2023-09-30"
        );
        List<List<String>> headlines = Arrays.asList(
            Arrays.asList("Вибори", "Спорт"),
            Arrays.asList("Економіка", "Шлюб знаменитості"),
            Arrays.asList("Погода", "Нова політика")
        );
        
        return new Newspaper(
            randomElement(titles),
            randomElement(dates),
            randomElement(headlines)
        );
    }

    private Almanac createRandomAlmanac() {
        List<String> titles = Arrays.asList(
            "Класична література", "Сучасні есе", "Поезія"
        );
        List<List<String>> works = Arrays.asList(
            Arrays.asList("Гордість і упередження", "Грозовий перевал"),
            Arrays.asList("Цифрова ера", "Міське життя"),
            Arrays.asList("Дорога не обрана", "Ода соловію")
        );
        
        return new Almanac(
            randomElement(titles),
            randomElement(works)
        );
    }

    private <T> T randomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    public void deleteByTitle(String title) {
        items.removeIf(item -> item.getTitle().equalsIgnoreCase(title));
    }

    public void displayAll() {
        items.forEach(item -> System.out.println(item.displayInfo()));
    }

    public List<LibraryItem> searchByTitle(String title) {
        return items.stream()
            .filter(item -> item.getTitle().equalsIgnoreCase(title))
            .collect(Collectors.toList());
    }

    public List<LibraryItem> searchByAuthor(String author) {
        return items.stream()
            .filter(item -> item instanceof Book)
            .filter(book -> ((Book) book).getAuthor().equalsIgnoreCase(author))
            .collect(Collectors.toList());
    }
}

public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog();
        catalog.testInitialization();
        
        System.out.println("Початковий каталог:");
        catalog.displayAll();

        catalog.addRandomItem();
        System.out.println("\nПісля додавання випадкового елемента:");
        catalog.displayAll();

        System.out.println("\nПошук за назвою 'Гобіт':");
        catalog.searchByTitle("Гобіт").forEach(item -> 
            System.out.println(item.displayInfo()));

        System.out.println("\nПошук за автором 'Дж. Роулінг':");
        catalog.searchByAuthor("Дж. Роулінг").forEach(item -> 
            System.out.println(item.displayInfo()));

        catalog.deleteByTitle("The Guardian");
        System.out.println("\nПісля видалення 'The Guardian':");
        catalog.displayAll();
    }
}