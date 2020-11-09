package pt.iade.helloworld.models;

public class Unit {
    
    String name;
    float grade;
    int semester;
    int ects;

    public Unit(String name, float grade, int semester, int ects) {
        this.name = name;
        this.grade = grade;
        this.semester = semester;
        this.ects = ects;
    }

    public String getName() {
        return name;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }

    public int getEcts() {
        return ects;
    }

    public boolean isApproved() {
        return grade >= 9.5;
    }
}
