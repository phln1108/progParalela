import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

def plotSerial(sort):
    data = pd.read_csv(f"./csvs/{sort}_Serial.csv")

    plt.plot(data['Size'], data['Time'])
    plt.xlabel('Size')
    plt.ylabel('Time')
    plt.title(f'{sort} Serial')   
    plt.savefig(f"./charts/{sort}_Serial.png")
    # plt.show()

def plotParallel(sort):
    data = pd.read_csv(f"./csvs/{sort}_Parallel.csv", sep=',')
    data['Time'] = data.groupby(['Size', 'Threads'])['Time'].transform('mean')

    for threads in data['Threads'].unique():
        plt.plot(data[data['Threads'] == threads]['Size'], data[data['Threads'] == threads]['Time'], label=f'{threads} Threads')
    
    plt.xlabel('Size')
    plt.ylabel('Time')
    plt.title(f'{sort} Parallel')
    plt.legend()
    plt.grid(True)
    plt.savefig(f"./charts/{sort}_Parallel.png")
    # plt.show()

def plot(sort):
    serial = pd.read_csv(f"./csvs/{sort}_Serial.csv")
    parallel = pd.read_csv(f"./csvs/{sort}_Parallel.csv", sep=',')
    # parallel['Time'] = parallel.groupby(['Size', 'Threads'])['Time'].transform('mean')

    fig , p = plt.subplots(nrows=1, ncols=2, figsize=(16, 8))

    for threads in parallel['Threads'].unique():
            p[1].plot(parallel[parallel['Threads'] == threads]['Size'], parallel[parallel['Threads'] == threads]['Time'], label=f'{threads} Threads')

    p[1].set_xlabel('Size')
    p[1].set_ylabel('Time')
    p[1].set_title(f'{sort} Parallel')
    p[1].legend()
    p[1].grid(True)

    p[0].plot(serial['Size'], serial['Time'])
    p[0].set_xlabel('Size')
    p[0].set_ylabel('Time')
    p[0].set_title(f'{sort} Serial') 

    plt.savefig(f"./charts/{sort}.png")
    plt.show()

    
plot("BubbleSort")
plot("CountingSort")
plot("MergeSort")
plot("QuickSort")