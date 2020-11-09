package pt.iade.helloworld.controllers;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.helloworld.models.Unit;

@RestController
@RequestMapping(path = "/api/java/tester")

public class JavaTesterController {
    private Logger logger = LoggerFactory.getLogger(JavaTesterController.class);

    /*--- Data types and operators ---*/

    String name = "Luís Felipe";
    int number = 20190972;
    float height = 1.79f;
    Boolean fanOfFootball = false;
    char favColorInitial = 'G';

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAuthor() {
        logger.info("Introducing WebApp author");

        String fan = (fanOfFootball ? "" : "not");

        return "Done by " + name + " with number " + number + ".\n" + "I am " + height + " tall and I am " + fan
                + " a fan of football" + "\n";
    }

    /*--- Boolean operations ---*/

    @GetMapping(path = "/access/{student}/{covid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getGreeting(@PathVariable("student") boolean isStudent, @PathVariable("covid") boolean hasCovid) {
        logger.info("Checking access to IADE");

        return isStudent && !hasCovid;
    }

    @GetMapping(path = "/required/{student}/{temperature}/{classType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getRequired(@PathVariable("student") boolean isStudent, @PathVariable("temperature") double hasCovid,
            @PathVariable("classType") String type) {

        logger.info("Checking if student should be in IADE");

        if (isStudent) {

            if (hasCovid < 34.5 || hasCovid > 37.5) {
                return false;
            }
            if (!type.equals("digital") && !type.equals("presential") && !type.equals("none")) {
                return false;
            }
            return true;
        }
        return false;
    }

    @GetMapping(path = "/evacuation/{fire}/{numberOfCovids}/{powerShutdown}/{comeBackTime}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean getEvacuation(@PathVariable("fire") boolean fire, @PathVariable("numberOfCovids") int covids,
            @PathVariable("powerShutdown") boolean power, @PathVariable("comeBackTime") double backTime) {

        logger.info("Checking if building should be evacuated");

        if (fire || covids > 5 || (!power && backTime > 15))
            return true;
        return false;
    }

    /*--- Arrays, Conditionals and Loops ---*/

    private static final double[] grades = { 18, 19, 17 };
    private static final String[] ucs = { "FP", "POO", "BD" };

    @GetMapping(path = "/academics/average", produces = MediaType.APPLICATION_JSON_VALUE)
    public double avgGrade() {
        logger.info("Checking average grade");

        double total = 0;
        for (double grade : grades) {
            total += grade;
        }
        return total / grades.length;
    }

    @GetMapping(path = "/academics/maxgrade", produces = MediaType.APPLICATION_JSON_VALUE)
    public double maxGrade() {
        logger.info("Checking max grade");

        double crntMax = 0;
        for (double grade : grades) {
            if (grade > crntMax) {
                crntMax = grade;
            }
        }
        return crntMax;
    }

    @GetMapping(path = "/academics/{uc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public double ucGrade(@PathVariable("uc") String uc) {
        logger.info("Getting grade for requested UC");

        if (uc.equals("FP"))
            return grades[0];
        if (uc.equals("POO"))
            return grades[1];
        if (uc.equals("BD"))
            return grades[2];
        return Double.NaN;
    }

    @GetMapping(path = "/academics/graderange/{minGrade}/{maxGrade}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int UCsInGradeRange(@PathVariable("minGrade") double min, @PathVariable("maxGrade") double max) {
        logger.info("Getting UC's within given grade range");

        int numUCsInGradeRange = 0;
        for (double grade : grades) {

            if (grade >= min && grade <= max) {
                numUCsInGradeRange++;
            }
        }
        return numUCsInGradeRange;
    }

    @GetMapping(path = "/academics/summary", produces = MediaType.APPLICATION_JSON_VALUE)
    public String summaryUCs() {
        logger.info("Getting summary of UC's");

        String summary = "";
        for (int i = 0; i < ucs.length; i++) {
            summary += ucs[i] + ": " + grades[i] + "\n";
        }
        return summary;
    }

    /*--- Objects, ArrayLists and post methods ---*/

    ArrayList<Unit> units = new ArrayList<Unit>();

    @PostMapping(path = "/units/")
    public Unit saveUnit(@RequestBody Unit unit) {
        logger.info("Added unit " + unit.getName());
        units.add(unit);
        return unit;
    }

    @GetMapping(path = "/units", produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<Unit> getUnits() {
        logger.info("Get " + units.size() + " Units");
        return units;
    }

    @GetMapping(path = "/units/average", produces = MediaType.APPLICATION_JSON_VALUE)
    public float avgGradeUCs() {
        logger.info("Getting average grade from UCs");
        
        float total = 0;
        for (Unit unit : units) {
            total += unit.getGrade();
        }
        return total/units.size();
    }

    @GetMapping(path = "/units/max", produces = MediaType.APPLICATION_JSON_VALUE)
    public float maxGradeUCs() {
        logger.info("Getting max grade from UCs");
        
        float crntMax = 0;
        for (Unit unit : units) {
            if (crntMax <= unit.getGrade()) {
                crntMax = unit.getGrade();
            }
        }
        return crntMax;
    }

    @GetMapping(path = "/units/ucgrade/{uc}", produces = MediaType.APPLICATION_JSON_VALUE)
    public float gradeFromUC(@PathVariable("uc") String uc) {
        logger.info("Getting grade from UC");
        
        for (Unit unit : units) {
            if (unit.getName().equals(uc)) return unit.getGrade();
        }

        return Float.NaN;
    }

    @GetMapping(path = "/units/ucsemester/{semester}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String[] UCsFromSemester(@PathVariable("semester") int semester) {
        logger.info("Getting UCs from semester");
        
        ArrayList<String> ucs = new ArrayList<String>();

        for (Unit unit : units) {
            if (unit.getSemester() == semester) {
                ucs.add(unit.getName());
            }
        }
        String[] UCs = (String[]) ucs.toArray();
        
        return UCs;
    }

    @GetMapping(path = "/units/ucsingraderange/{min}/{max}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int UCsInGradeRange(@PathVariable("min") int min, @PathVariable("max") int max) {
        logger.info("Getting # of UCs within grade range");
        
        int total = 0;
        for (Unit unit : units) {
            if (unit.getGrade() >= min && unit.getGrade() <= max) {
                total++;
            }
        }
        
        return total;
    }

}