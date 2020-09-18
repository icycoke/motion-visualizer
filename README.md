# Motion Visualizer

A simple Android app which can visualize the data from specific motion sensors.

## Project Structure

All source code files are under `com.icycoke.motion_visualizer` package. Two `DialogFragment` files are under `com.icycoke.motion_visualizer.dialog` package.

The collected data file will be saved as `getFilesDir().getAbsolutePath() + "/data.csv"` which is `/data/data/com.icycoke.motion_visualizer/files/data.csv` as default. 
