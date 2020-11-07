# Motion Visualizer
A simple Android app which can visualize the data from specific motion sensors.
![Motion Visualizer](https://github.com/icycoke/img-repo/blob/main/motion-visualizer/screenshot.png)

## Project Structure
![Motion Visualizer Flowchart](https://github.com/icycoke/img-repo/blob/main/motion-visualizer/motion-visualizer-flowchart.png)

All source code files are under `com.icycoke.motion_visualizer` package. Two `DialogFragment` files are under `com.icycoke.motion_visualizer.dialog` package.

The collected data file will be saved as `getFilesDir().getAbsolutePath() + "/data.csv"` which is `/data/data/com.icycoke.motion_visualizer/files/data.csv` as default.