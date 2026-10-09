public class week_7_Practice_problems_4 {
    interface Playable {
        String play();
        String play(int fromSecond);
        String pause();
    }

    static abstract class MediaFile {
        private static int counter = 1000;
        private final String fileId;

        public MediaFile() {
            counter++;
            fileId = "MF-" + counter;
        }

        public String getFileId() {
            return fileId;
        }

        public abstract String getFormatInfo();
    }

    static class AudioFile extends MediaFile implements Playable {
        private final String title;

        public AudioFile(String title) {
            if (title == null || title.trim().isEmpty())
                throw new IllegalArgumentException("Title cannot be blank.");
            this.title = title;
        }

        @Override
        public String play() {
            return "Playing audio: " + title;
        }

        @Override
        public String play(int fromSecond) {
            if (fromSecond < 0) throw new IllegalArgumentException("Second cannot be negative.");
            return "Playing audio: " + title + " from "
                    + (fromSecond / 60) + ":" + String.format("%02d", fromSecond % 60);
        }

        @Override
        public String pause() {
            return "Paused audio: " + title;
        }

        @Override
        public String getFormatInfo() {
            return "Audio file, ID: " + getFileId();
        }
    }

    static class Podcast implements Playable {
        private final String showName;
        private final int episodeNumber;

        public Podcast(String showName, int episodeNumber) {
            if (showName == null || showName.trim().isEmpty())
                throw new IllegalArgumentException("Show name cannot be blank.");
            if (episodeNumber <= 0)
                throw new IllegalArgumentException("Episode number must be positive.");
            this.showName = showName;
            this.episodeNumber = episodeNumber;
        }

        @Override
        public String play() {
            return "Streaming episode " + episodeNumber + " of " + showName;
        }

        @Override
        public String play(int fromSecond) {
            if (fromSecond < 0) throw new IllegalArgumentException("Second cannot be negative.");
            return "Streaming episode " + episodeNumber + " of " + showName + " from " + fromSecond + " seconds";
        }

        @Override
        public String pause() {
            return "Paused episode " + episodeNumber + " of " + showName;
        }
    }

    public static void launchAll(Playable[] items) {
        for (Playable item : items) {
            System.out.println(item.play());
        }
    }

    public static void main(String[] args) {
        AudioFile audio = new AudioFile("Morning Jazz");
        Podcast podcast = new Podcast("Tech Talk", 12);

        System.out.println(audio.play());
        System.out.println(audio.play(30));
        System.out.println(audio.getFormatInfo());
        System.out.println(podcast.play());

        // Upcasting: AudioFile reference stored as Playable.
        Playable ref = audio;
        System.out.println(ref.play());

        launchAll(new Playable[]{ref, podcast});
    }
}
