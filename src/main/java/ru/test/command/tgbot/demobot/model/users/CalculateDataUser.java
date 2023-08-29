package ru.test.command.tgbot.demobot.model.users;

public class CalculateDataUser {
    private long userId;
    private double firstNumber;
    private double secondNumber;
    private char sign;
    private int step = 0;
    private double result;

    public CalculateDataUser(long userId) {
        this.userId = userId;
    }

    public CalculateDataUser(){}

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getFirstNumber() {
        return firstNumber;
    }

    public double getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(double secondNumber) {
        this.secondNumber = secondNumber;
    }

    public void setFirstNumber(double firstNumber) {
        this.firstNumber = firstNumber;
    }

    public char getSign() {
        return sign;
    }

    public void setSign(char sign) {
        this.sign = sign;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return firstNumber + " " + sign + " " + secondNumber + " = " + result + "\n";
    }
}
