Please bear in mind, this is a project I learned Java on, so use it with caution.

The compiled jar file can be found in out/artefacts.

**Only 2D is supported. Trying to export/work on a 3D drawing will result in the 3rd dimension (Z axis) being omitted.**


Not every DXF structure is fully supported.

Full support
- LINE
- LWPOLYLINE
- POLYLINE
- BLOCK
- TEXT
- MTEXT

Partial Support (not all attributes are supported, or no tests have been done)
- ARC (length, export of arc-related attributes, xdata)
- Spline (as above)
- Circle (as above)
- Point (should be read, but no export whatsoever)
- SOLID (only as part of BLOCK)
- VERTEX (only as part of BLOCK or polyline)

No Support at all:
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


