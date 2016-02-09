
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;




/**
 *
 * STOP. DO NOT LOOK AT THIS FILE TOO MUCH. You will get errors here (in test())
 * if you do not properly implement your methods in BookDB1.
 *
 *
 * @author dr
 */
class BookDBTest {

    BookDB lib = null; // This is the "model"
    BookDBView view = null;  // This is the "view"

    
    
    public BookDBTest() {
        
        lib = new BookDB();
        view = new BookDBView();
        
    }

    /**
     * Run test of model class.
     */
    public void testModel() {
        System.out.println("Testing in progress.");
        int idOMM = lib.addBook("John Steinbeck", "Of Mice and Men");
        int idSM = lib.addBook("Richard Russo", "Straight Man");
        int idMMWV = lib.addBook("John Gray", "Men Are from Mars, Women Are from Venus");
        ArrayList<Integer> results = lib.searchBook("Men");
        if (!(results.size() == 2 && results.contains(idOMM) && results.contains(idMMWV))) {
            System.out.println("Error searching for 'Men'");
        }
        results = lib.searchBook("from");
        if (!(results.size() == 1 && results.contains(idMMWV))) {
            System.out.println("Error searching for 'from'");
        }
        if (lib.numBooks() != 3) {
            System.out.println("Error running numBooks - did not return 3");
        }
        lib.removeBook(idMMWV);
        if (lib.numBooks() != 2) {
            System.out.println("Error deleting book.");
            System.out.println("or: Error running numBooks - did not return 2");
        }
        String retrievedBook = lib.getBook(idSM);
        if (!(retrievedBook.contains("Russo") && retrievedBook.contains("Straight Man"))) {
            System.out.println("Error running getBook.  Returned string: " + retrievedBook);
            System.out.println("(Should be similar to Russo, Straight Man)");
        }

        System.out.println("Testing finished.");

    }

    /**
     * Test model for scalability and display results graphically.
     */
    public void stressTestModel() {

        final int iterations = 20;
        final int repeatsPerIteration = 1000;
        final int itemsAddedPerIteration = 10000;

        GraphingData gdSearch = ngraph("Searching");
        GraphingData gdAdd = ngraph("Adding");
        GraphingData gdRemove = ngraph("Removing");
        GraphingData gdCount = ngraph("Counting");

        int[] addTimes = new int[iterations];
        int[] searchTimes = new int[iterations];
        int[] removeTimes = new int[iterations];
        int[] countTimes = new int[iterations];

        int[] firstAddedItems = new int[repeatsPerIteration];
        int c = 0;
        long t;
        for (int j = 0; j < iterations; j++) {
            t = getCpuTime();
            for (int i = 0; i < itemsAddedPerIteration; i++) {
                c++;
                int item = lib.addBook("JohnDoe " + c, "Some Title Number " + c);
                if (i < repeatsPerIteration) {
                    firstAddedItems[i] = item;
                }
            }
            addTimes[j] = (int) ((getCpuTime() - t) / itemsAddedPerIteration);
            // now search some item near the end and time it
            t = getCpuTime();
            for (int i = 0; i < repeatsPerIteration; i++) {
                lib.searchBook(" " + (c - 1));
            }
            searchTimes[j] = (int) ((getCpuTime() - t) / repeatsPerIteration);
            // now count items and time it
            t = getCpuTime();
            for (int i = 0; i < repeatsPerIteration; i++) {
                int count = lib.numBooks();
            }
            countTimes[j] = (int) ((getCpuTime() - t) / repeatsPerIteration);
            // now remove some item at the beginning and time it
            t = getCpuTime();
            for (int i = 0; i < repeatsPerIteration; i++) {
                lib.removeBook(firstAddedItems[i]);
            }
            removeTimes[j] = (int) ((getCpuTime() - t) / repeatsPerIteration);
            System.out.println(c + "\t   " + addTimes[j] + "\t   " + searchTimes[j]);
            //System.out.println(c); // indicate progress
        }
        gdAdd.setData(addTimes);
        gdSearch.setData(searchTimes);
        gdRemove.setData(removeTimes);
        gdCount.setData(countTimes);
        gdAdd.display();
        gdSearch.display();
        gdRemove.display();
        gdCount.display();

    }

    /**
     * Get CPU time in nanoseconds.
     */
    private long getCpuTime() {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime() : 0L;
    }

    private GraphingData ngraph(String title) {
        return new GraphingData(lib.getClass() + ": " + title, "Number of items in DB", "Time");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BookDBTest libraryController = new BookDBTest();

        libraryController.testModel();
         libraryController.stressTestModel();

    }

}
