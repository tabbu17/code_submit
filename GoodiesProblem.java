package com.demo.cloudstream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GoodiesProblem {

  public static void main(String args[]) throws IOException {
    String inputFilePath = "C:/Users/tabbu/Desktop/code_high_pick_software/test/sample_input.txt";
    String outputFilePath = "C:/Users/tabbu/Desktop/code_high_pick_software/test/output.txt";

    // Taking Input from file to an input Array.
    File file = new File(inputFilePath);
    FileInputStream inputStream = new FileInputStream(file);
    Scanner sc = new Scanner(inputStream);
    String[] input = new String[10000]; // Assuming max 10000 items to be present in input since max
                                        // count not provided in statement.
    int lines = 0;
    while (sc.hasNext()) {
      input[lines++] = sc.nextLine();
    }
    sc.close();

    // Fetching Number of Employees from the input array
    int numOfEmployees = Integer.parseInt(input[0].split(": ")[1]);

    // Fetching the item names and item prices from the input array
    GoodiesProblem obj = new GoodiesProblem();
    Item[] items = new Item[10000];
    int n = 0;
    for (int i = 4; i < lines; i++) {
      items[n] = obj.new Item();
      items[n].setName(input[i].split(": ")[0]);
      items[n].setPrice(Integer.parseInt(input[i].split(": ")[1]));
      n++;
    }

    // Sort Items based on prices in ascending order.
    sortPrices(items, n);

    // Assumption: Number of employees will always be less than or equal to number of items
    // provided.
    int minDiff = Integer.MAX_VALUE;
    int minDiffIndex = -1;
    for (int i = 0; i <= n - numOfEmployees; i++) {
      int diff = items[i + numOfEmployees - 1].getPrice() - items[i].getPrice();
      if (diff < minDiff) {
        minDiff = diff;
        minDiffIndex = i;
      }
    }

    // Write Output
    StringBuffer buffer = new StringBuffer();
    File dest = new File(outputFilePath);

    buffer.append("The goodies selected for distribution are: \n\n");
    for (int i = minDiffIndex; i < minDiffIndex + numOfEmployees; i++) {
      buffer.append(items[i].getName() + ": " + items[i].getPrice() + "\n");
    }
    buffer.append(
        "\nAnd the difference between the chosen goodie with highest price and the lowest price is "
            + minDiff);
    FileWriter writer = new FileWriter(dest);
    writer.write(buffer.toString());
    writer.flush();
    writer.close();
  }

  private static void sortPrices(Item[] items, int n) {
    quicksort(items, 0, n - 1);
  }

  private static int partition(Item arr[], int low, int high) {

    Item pivot = arr[high];
    int i = (low - 1);
    for (int j = low; j < high; j++) {
      if (arr[j].getPrice() <= pivot.getPrice()) {
        i++;
        Item temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }
    Item temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;

    return i + 1;
  }

  private static void quicksort(Item arr[], int low, int high) {
    if (low < high) {
      int pi = partition(arr, low, high);
      quicksort(arr, low, pi - 1);
      quicksort(arr, pi + 1, high);
    }
  }

  private class Item {
    private String name;
    private int price;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getPrice() {
      return price;
    }

    public void setPrice(int price) {
      this.price = price;
    }
  }

}

