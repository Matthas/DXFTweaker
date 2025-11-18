Please bear in mind, this is a project I learned Java on, so use it with caution.

The compiled jar file can be found in out/artefacts.

<h1 style="font-size:40px;">About</h1>
This is my small project on which I have been studying JAVA. I tried my best to make the code as readable as I could and to try to follow the design principles I have learn/heard.

From the user perspective, the app can convert a **DXF file into a CSV file** (please read below what elements are supported).

From the developer's perspective. The app parses the entire DXF drawing file and store each element in a separate HashMap so further work can be achieved without needing extensive knowledge of DXF.
After loading the DXF file, the app pauses work with the created hashmaps for:
- separate DXF elements like polyline, line, point, inserts,
- HashMap of AutoCAD Blocks (predefined by user in Autocad file),
- Each Block element also contains HashMap of all subelements that make up that block.

<h3 style="font-size:5px;">Only 2D is supported. Trying to export/work on a 3D drawing will result in the 3rd dimension (Z axis) being omitted.</h3>


<h1 style="font-size:40px;">List of supported elements</h1>
<h4><b>Full Support</b></h4>

- LINE  
- LWPOLYLINE
- POLYLINE
- BLOCK
- TEXT
- MTEXT
 
<h4><b>Full Support</b> (not all attributes are supported, or no tests have been done)</h4>

- ARC (length, export of arc-related attributes, xdata)
- Spline (as above)
- Circle (as above)
- Point (should be read, but no export whatsoever)
- SOLID (only as part of BLOCK)
- VERTEX (only as part of BLOCK or polyline)

<h4><b>No support at all</b></h4>

- Rectangle, 3DFace, Trace (no support at all)
- POLYFACE MESH
- BODY
- REGION
- SURFACE
- SOLID3D
- 3D POLYLINE

How to use:
1. Run jar file
2. Click DXF Load button
  <img width="490" height="318" alt="DXFTOOLBOX" src="https://github.com/user-attachments/assets/bff48d71-6d1f-4be5-a7b4-4c3a602ebf4d" />
  
3. Wait until it says "Finished"
4. Click "Export to CSV" and save your file.


