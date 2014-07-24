package es.qiu.utils;

import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.trees.PennTreeReader;
import edu.stanford.nlp.trees.Tree;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.*;

/**
 * Hello world!
 *
 */
public class GoldMaker
{
    public void generate(String fileName) throws IOException {
        PennTreeReader ptReader = loadPTBCorpus(fileName);
        Tree currentTree = ptReader.readTree();
        while (currentTree != null) {
            System.out.println(transform(currentTree));
        }

    }

    private String transform(Tree currentTree) {
        currentTree.label().setValue("TOP");
        return currentTree.toString();
    }

    private PennTreeReader loadPTBCorpus(String fileName) {
        PennTreeReader ptReader = null;
        try {
            ptReader = new PennTreeReader(new BufferedReader(new FileReader(new File(fileName))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ptReader;
    }

    public static void main( String[] args )
    {
        ArgumentParser parser = ArgumentParsers.newArgumentParser("ptb2gold").description("Transform Penn Treebank in to gold format which can be used by berkeley parser and Dan Bikel Parser");
        parser.addArgument("file").metavar("F").help("Nmae of the file which contains Penn Treebank style data");
        try {
            Namespace res = parser.parseArgs(args);
            GoldMaker goldMaker = new GoldMaker();
            goldMaker.generate((String) res.get("file"));
        } catch (ArgumentParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

