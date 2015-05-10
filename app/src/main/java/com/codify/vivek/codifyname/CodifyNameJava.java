package com.codify.vivek.codifyname;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vivek on 09/05/2015.
 */
public class CodifyNameJava {
    // Initialize and start the Encoding/Decoding
    public final String CodifyName(String input, String name, Boolean EncodeMe) {
        char[] inputChar = input.toCharArray();
        String[] nameString = name.replaceAll(" ", "").split("");
        // Remove Duplicates
        Set<String> temp = new HashSet<String>(Arrays.asList(nameString));
        nameString = temp.toArray(new String[temp.size()]);
        // Convert to Char Array
        char[] nameChar = getString(nameString).toCharArray();
        char[] alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz.,!%^&*-_=+[]{}#~'@/?<>|1234567890(); ".toCharArray();
        List<Integer> listNamePositions = GetAlphabetPositions(nameChar, alphabets);
        while (listNamePositions.size() > 5)
            listNamePositions.remove(listNamePositions.size() - 1);
        List<List<Integer>> posNamePermutations = GetPermutations(listNamePositions.toArray(new Integer[listNamePositions.size()]));
        Map<Character, List<Integer>> alphaPosNamePermutation = AssignAlphabetPositionPermutation(alphabets, posNamePermutations);
        String result = "";
        if (EncodeMe) {
            result = EncoderCodifyName(inputChar, alphabets, alphaPosNamePermutation);
        } else {
            result = DecoderCodifyName(inputChar, alphabets, alphaPosNamePermutation, listNamePositions.size());
        }
        return result;
    }

    // Convert String Array to string
    public String getString(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for (String s : arr) {
            builder.append(s);
        }
        return builder.toString();
    }

    // Decode input
    protected String DecoderCodifyName(char[] inputDecode, char[] alphabets, Map<Character, List<Integer>> alphaPosNamePermutation, int divide) {
        List<Integer> alphaPos = new ArrayList<Integer>();
        String decodedString = "";
        // Uncomment to binary search
        //Arrays.sort(alphabets);
        for (int i = 0; i < inputDecode.length; i++) {
            //alphaPos.add(Arrays.binarySearch(alphabets, inputDecode[i]));
            alphaPos.add(new String(alphabets).indexOf(inputDecode[i]));
            if ((i + 1) % divide == 0) {
                decodedString += getKeyByValue(alphaPosNamePermutation, alphaPos);
                alphaPos = new ArrayList<Integer>();
            }
        }
        return decodedString;
    }

    public Character getKeyByValue(Map<Character, List<Integer>> map, List<Integer> value) {
        for (Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


    // Enccode input
    protected String EncoderCodifyName(char[] input, char[] alphabets, Map<Character, List<Integer>> alphaPosNamePermutation) {
        String encodedString = "";
        for (int indexInput = 0; indexInput < input.length; indexInput++) {
            List<Integer> pos = alphaPosNamePermutation.get(input[indexInput]);
            for (int indexPos = 0; indexPos < pos.size(); indexPos++) {
                encodedString += alphabets[pos.get(indexPos)];
            }
        }
        return encodedString;
    }

    // Assign every character in the alphabets a permutated combination
    protected Map<Character, List<Integer>> AssignAlphabetPositionPermutation(char[] alphabets, List<List<Integer>> posPermutations) {
        Map<Character, List<Integer>> alphaPosPer = new HashMap<Character, List<Integer>>();
        for (int i = 0; i < alphabets.length; i++) {
            alphaPosPer.put(alphabets[i], posPermutations.get(i));
        }
        return alphaPosPer;
    }

    // Get char position
    protected List<Integer> GetAlphabetPositions(char[] name, char[] alphabets) {
        List<Integer> pos = new ArrayList<Integer>();
        // Uncomment to binary search
        //Arrays.sort(alphabets);
        for (int index = 0; index < name.length; index++) {
            pos.add(new String(alphabets).indexOf(name[index]));
            //pos.add(Arrays.binarySearch(alphabets, name[index]));
        }
        return pos;
    }

    // Calculate permutation
    protected List<List<Integer>> GetPermutations(Integer[] num) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        //start from an empty list
        result.add(new ArrayList<Integer>());

        for (int i = 0; i < num.length; i++) {
            //list of list in current iteration of the array num
            List<List<Integer>> current = new ArrayList<List<Integer>>();

            for (List<Integer> l : result) {
                // # of locations to insert is largest index + 1
                for (int j = 0; j < l.size() + 1; j++) {
                    // + add num[i] to different locations
                    l.add(j, num[i]);

                    ArrayList<Integer> temp = new ArrayList<Integer>(l);
                    current.add(temp);

                    //System.out.println(temp);

                    // - remove num[i] add
                    l.remove(j);
                }
            }

            result = new ArrayList<List<Integer>>(current);
        }

        return result;
    }
}
