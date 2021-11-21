package pl.klemp.ian.myrecipes.utils.scraper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.klemp.ian.myrecipes.exception.UnableToRetrieveUrlException;
import pl.klemp.ian.myrecipes.utils.scraper.reader.PageReader;
import pl.klemp.ian.myrecipes.utils.scraper.reader.SchemaReader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

public class ReaderSelector {

    private final String url;
    private final Document document;

    public ReaderSelector(String url) {
        this.url = url;
        this.document = getDocument();
    }

    /**
     * If exists, return a websiteReader (extends {@link PageReader} which has scraping methods tailored for this website)
     *  class must be named like:
      *      '.websiteReaders.DomainnamePageReader.java', e.g.
      *      '.websiteReaders.AllrecipesPageReader.java' for https://www.allrecipes.com
     *
     * Otherwise return {@link SchemaReader} which will search for a <a href="https://schema.org">Schema.org</a> json-ld recipe
     *  TODO MicrodataReader
     *  TODO RdfaReader
     *
     *  TODO DefaultReader
     *  TODO logging urls that need PageReaders to be created for them
     *  If no recipe has been found, a default reader will try to find a recipe.
     *  Should also send some message that scraped recipe might be incorrect.
     */

    public PageReader select() {
        try {
            Class<?> cls = Class.forName(PageReader.class.getPackage().getName() + ".websiteReaders." + getDomainName() + "PageReader");
            return (PageReader) cls.getDeclaredConstructor(Document.class).newInstance(document);
        } catch (NoSuchMethodException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return new SchemaReader(document);
        } catch (URISyntaxException | NullPointerException e) {
            throw new UnableToRetrieveUrlException(url);
        }
    }

    private String getDomainName() throws URISyntaxException {
        String domain = new URI(url).getHost();
        domain = domain.startsWith("www.") ? domain.substring(4):domain;
        String[] parts = domain.split("\\.");

        return StringUtils.capitalize(parts[0].toLowerCase());
    }

    private Document getDocument() {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new UnableToRetrieveUrlException(url);
        }
    }
}
