package kz.greepto.gpen.editors.gpen.model.paint

import java.util.ArrayList
import java.util.List
import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.editors.gpen.style.TableStyle

class PaintTable extends AbstractPaint {
  Table table

  new(Table table) {
    this.table = table
  }

  override getFigureId() { table.id }

  static class Cell {
    int width
    int height
    String text
    boolean adhereLeft = false
    boolean adhereRight = false

    boolean adhereTop = false
    boolean adhereBottom = false

    def void applyText(String text) {
      this.text = text
      applyPrefixes()
      applyPostfixes()
    }

    def Size getSize() { Size.from(width, height) }

    private def void applyPostfixes() {
      while (true) {
        if (!adhereRight && text.endsWith("~")) {
          adhereRight = true
          text = text.substring(0, text.length - 1)
        } else if (!adhereBottom && text.endsWith("^")) {
          adhereBottom = true
          text = text.substring(0, text.length - 1)
        } else {
          return
        }
      }
    }

    private def void applyPrefixes() {
      while (true) {
        if (!adhereLeft && text.startsWith("~")) {
          adhereLeft = true
          text = text.substring(1)
        } else if (!adhereTop && text.startsWith("^")) {
          adhereTop = true
          text = text.substring(1)
        } else {
          return
        }
      }
    }

  }

  static class Line {
    boolean head = false
    boolean sel = false
    val List<Cell> cellList = new ArrayList

    def FontDef extractFont(TableStyle style) {
      var cf = style.contentFont ?: FontDef.arial.h(12)
      if(head) style.headerFont ?: cf else if(sel) style.selFont ?: cf else cf
    }

    def Kolor extractTextKolor(TableStyle style) {
      var cf = style.contentFgColor ?: Kolor.BLACK
      if(head) style.headerFgColor ?: cf else if(sel) style.selFgColor ?: cf else cf
    }

    def Kolor extractBgKolor(TableStyle style) {
      var cf = style.contentBgColor ?: Kolor.WHITE
      if(head) style.headerBgColor ?: cf else if(sel) style.selBgColor ?: cf else cf
    }

  }

  override PlaceInfo work(Vec2 mouse) {
    if(mouse === null) return new PlaceInfo(table.rect, rectMouseInfo(mouse, table.rect, true))

    var ps = PaintStatus.from(table.rect.contains(mouse), table.sel)
    var calc = styleCalc.calcForTable(table, ps)

    dp.font = calc.contentFont

    dp.style.foreground = calc.borderColor

    var p = table.point.copy

    var lines = extractLines(table)

    for (line : lines) {
      var max = 0
      for (cell : line.cellList) {
        var h = calcCellHeight(cell, line, calc)
        if(max < h) max = h
      }
      for (cell : line.cellList) {
        cell.height = max
      }
    }

    for (line : lines) {
      var h = 0
      for (cell : line.cellList) {
        drawCell(calc, table.rect, p, cell, line)
        p.x += cell.width
        if(h < cell.height) h = cell.height
      }
      p.x = table.x
      p.y += h
    }

    dp.from(table.rect).draw

    if (table.sel && calc.focusColor != null) {
      dp.style.foreground = calc.focusColor
      drawAroundFocus(table.rect)
    }

    return new PlaceInfo(table.rect, rectMouseInfo(mouse, table.rect, true))
  }

  def static List<Line> extractLines(Table table) {

    var ret = newArrayList()

    for (lineStr : (table.content ?: '').split('\n')) {
      var line = new Line()
      ret += line

      var str = lineStr
      if (str.startsWith("#")) {
        line.head = true
        str = str.substring(1)
      } else if (str.startsWith("*")) {
        line.sel = true
        str = str.substring(1)
      }

      for (cellStr : str.split('\\|')) {
        var cell = new Cell
        line.cellList += cell
        cell.applyText(cellStr)

      }
    }

    var colCount = 0
    for (line : ret) {
      var s = line.cellList.size
      if(colCount < s) colCount = s
    }

    for (line : ret) {
      var s = line.cellList.size
      if (s < colCount) {
        for (var i = 0, var C = colCount - s; i < C; i++) {
          var c = new Cell
          c.adhereLeft = true
          c.text = ''
          line.cellList += c
        }
      }
    }

    val defWidth = 50

    val List<Integer> widths = new ArrayList
    for (str : (table.colWidths ?: '').split('\\|')) {
      try {
        widths += Integer.valueOf(str)
      } catch (NumberFormatException e) {
        widths += defWidth
      }
    }

    if (widths.size < colCount) {
      for (var i = 0, var C = colCount - widths.size; i < C; i++) {
        widths += defWidth
      }
    }

    for (line : ret) {
      for (var i = 0; i < colCount; i++) {
        line.cellList.get(i).width = widths.get(i)
      }
    }

    return ret
  }

  def int calcCellHeight(Cell cell, Line line, TableStyle ts) {
    dp.font = line.extractFont(ts)
    var textSize = dp.str(cell.text).size

    var drawSize = ts.cellPadding?.apply(textSize) ?: textSize

    return drawSize.height
  }

  def void drawCell(TableStyle ts, Rect tableRect, Vec2 p, Cell cell, Line line) {

    dp.font = line.extractFont(ts)
    var textSize = dp.str(cell.text).size

    var drawSize = ts.cellPadding?.apply(textSize) ?: textSize

    var pleft = ts.cellPadding?.left
    var ptop = ts.cellPadding?.top
    var dx = pleft
    var dy = ptop
    if (drawSize.height > cell.height) {
      throw new IllegalArgumentException('drawSize.height > cell.height')
    }

    if (cell.adhereTop && cell.adhereBottom || !cell.adhereTop && !cell.adhereBottom) {
      dy += (cell.height - drawSize.height) / 2 //make center in vertical
    } else if (cell.adhereBottom) {
      dy += cell.height - drawSize.height //make bottom in vertical
    }

    if (cell.adhereLeft && cell.adhereRight || !cell.adhereLeft && !cell.adhereRight) {
      dx += (cell.width - drawSize.width) / 2 //make center in horizontal
    } else if (cell.adhereRight) {
      dx += cell.width - drawSize.width //make bottom in horizontal
    }

    var cellSize = cell.size

    var clipRect = Rect.pointSize(p, cellSize) && tableRect

    dp.style.background = line.extractBgKolor(ts)

    dp.from(clipRect).clip.fill

    dp.style.foreground = line.extractTextKolor(ts)

    dp.str(cell.text).draw(p.x + dx, p.y + dy)

    dp.style.foreground = ts.borderColor

    dp.from(p + #[cellSize.width - 1, 0])//
    .shift(0, cellSize.height - 1).line//
    .shift(-cellSize.width, 0).line

    dp.clearClipping
  }
}
