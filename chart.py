import matplotlib.pyplot as plt
import pandas as pd
import numpy as np

def plot(sort):
    serial = pd.read_csv(f"./csvs/{sort}_Serial.csv")
    parallel = pd.read_csv(f"./csvs/{sort}_Parallel.csv")
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
    p[0].grid(True)

    # Determine the maximum time value from both datasets
    max_time = max(serial['Time'].max(), parallel['Time'].max())

    # Set the same y-axis limit for both subplots
    p[0].set_ylim(0, max_time)
    p[1].set_ylim(0, max_time)

    plt.savefig(f"./charts/{sort}.png")
    # plt.show()

    
# plot("BubbleSort")
plot("CountingSort")
plot("MergeSort")
plot("QuickSort")