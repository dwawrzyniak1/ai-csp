package csp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Variable {

    private int value;
    private boolean[] domain;
    private List<Predicate<Variable[][]>> constraints;
    private int row;
    private int column;
    private boolean isConstant;

    public Variable(int value, boolean[] domain) {
        this(value);
        this.domain = domain;
    }

    public Variable(int value) {
        this.value = value;
        isConstant = value != 0;
        this.constraints = new ArrayList<>();
    }

    public Variable(Variable other){
        this.value = other.getValue();
        this.domain = other.getDomain().clone();
        this.row = other.row;
        this.column = other.column;
        this.constraints = other.constraints; // no need to deep copy this list
    }

    public int[] getDomainValues() {
        int[] values = new int[getDomainSize()];
        int index = 0;
        for(int i = 0; i < domain.length; i++){
            if(domain[i]){
                values[index++] = i + 1;
            }
        }
        return values;
    }

    public void addConstraint(Predicate<Variable[][]> constraint){
        constraints.add(constraint);
    }

    public void changeDomainAtPosition(int valueIndex, boolean isInDomain){
        domain[valueIndex] = isInDomain;
    }

    public boolean isAssigned(){
        return value != 0;
    }

    public boolean isConstant(){
        return isConstant;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValueAndUpdateDomain(int value, int prevValue){
        if(prevValue != 0){
            domain[prevValue - 1] = true;
        }
        this.value = value;
        domain[value - 1] = false;
    }

    public boolean[] getDomain() {
        return domain;
    }

    public void setDomain(boolean[] domain) {
        this.domain = domain;
    }

    public List<Predicate<Variable[][]>> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Predicate<Variable[][]>> constraints) {
        this.constraints = constraints;
    }

    @Override
    public String toString() {
        return "csp.Variable{" +
                "value=" + value +
                ", domain=" + Arrays.toString(domain) +
                ", row=" + row +
                ", column=" + column +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return value == variable.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public boolean isValueConsistent(int value, Variable[][] state) {
        int initialValue = this.value;
        this.value = value;
        boolean isConsistent = isConsistent(state);
        this.value = initialValue;
        return isConsistent;
    }

    public boolean isConsistent(Variable[][] state){
        for(Predicate<Variable[][]> constraint : constraints){
            if(!constraint.test(state)){
                return false;
            }
        }
        return true;//constraints.stream().allMatch(constraint -> constraint.test(state));
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setCoordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void updateDomain(Variable[][] state) {
        for(int valueIndex = 0; valueIndex < domain.length; valueIndex++){
            if(valueIndex != value - 1){
                domain[valueIndex] = isValueConsistent(valueIndex + 1, state);
            }
        }
    }

    public int getDomainSize(){
        int domainSize = 0;
        for(boolean domainValue : domain){
            if(domainValue){
                domainSize++;
            }
        }
        return domainSize;
    }

    public int calculateDomainChanges(Variable[][] state) {
        int domainConstraintingRatio = 0;
        for(int valueIndex = 0; valueIndex < domain.length; valueIndex++){
            boolean isConsistent = isValueConsistent(valueIndex + 1, state);
            if(domain[valueIndex] && !isConsistent){
                domainConstraintingRatio++;
            }
            else if(!domain[valueIndex] && isConsistent){
                domainConstraintingRatio--;
            }
        }
        return domainConstraintingRatio;
    }
}
