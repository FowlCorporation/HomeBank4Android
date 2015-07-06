package com.fowlcorp.homebank4android.utils;

import com.fowlcorp.homebank4android.model.Operation;

import java.util.List;

/**
 * Created by Martin on 06/07/2015.
 */
public class Tools {

    public static boolean compareOperations(Operation operation, Operation operationToCompare){
        if(operation.getAmount()!=operationToCompare.getAmount()){
            return false;
        }
        if(!operation.getWording().equals(operationToCompare.getWording())){
            return false;
        }
        if(!operation.getPayee().getName().equals(operationToCompare.getPayee().getName())){
            return false;
        }
        /*if(operation.getDate().equals(operationToCompare.getDate())){
            return false;
        }*/
        return true;
    }

    public static int getOperationPosition(List<Operation> listOp, Operation operation){
        int result = -1;
        for(int i=0;i<listOp.size();i++){
            if(compareOperations(operation, listOp.get(i))){
                return i;
            }
        }
        return result;
    }
}
