package kz.greepto.gpen.editors.gpen.model.paint

import java.util.ArrayList
import java.util.List
import kz.greepto.gpen.drawport.DrawPort
import kz.greepto.gpen.drawport.FontDef
import kz.greepto.gpen.drawport.Kolor
import kz.greepto.gpen.drawport.Kursor
import kz.greepto.gpen.drawport.Rect
import kz.greepto.gpen.drawport.Size
import kz.greepto.gpen.drawport.Vec2
import kz.greepto.gpen.editors.gpen.model.Table
import kz.greepto.gpen.editors.gpen.prop.ValueSetter
import kz.greepto.gpen.editors.gpen.style.PaintStatus
import kz.greepto.gpen.editors.gpen.style.TableStyle

import static kz.greepto.gpen.editors.gpen.model.paint.PaintTable.*
import kz.greepto.gpen.editors.gpen.action.OperModify

class PaintTable extends AbstractPaint {
  private static val SEP_WIDTH = 2
  private static val MIN_COL_WIDTH = 50
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

  override PaintResult work(Vec2 mouse) {
    if(mouse === null) return simpleRect(table.rect)

    var ps = PaintStatus.from(table.rect.contains(mouse), table.sel)
    var calc = styleCalc.calcForTable(table, ps)

    dp.font = calc.contentFont

    dp.style.background = calc.bgColor

    dp.from(table.rect).fill

    dp.style.foreground = calc.borderColor

    var p = table.point.copy

    var lines = extractLines(table)

    var tableHeight = 0

    for (line : lines) {
      var max = 0
      for (cell : line.cellList) {
        var h = calcCellHeight(cell, line, calc)
        if(max < h) max = h
      }
      tableHeight += max
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

    if (tableHeight > 0) {
      var line = lines.get(0)
      var left = 0
      var colIndex = -1
      for (cell : line.cellList) {
        left += cell.width
        colIndex++
        var x = table.x + left - 1
        var y = table.y - 1
        dp.style.foreground = Kolor.CYAN
        dp.style.background = Kolor.CYAN
        var r = Rect.from(x - SEP_WIDTH, y, 2 * SEP_WIDTH + 1, tableHeight + 1)
        if (r.contains(mouse)) {
          dp.from(r).draw
          return modiColWidth(colIndex, Vec2.from(x, y), tableHeight, table.rect, mouse)
        }
      }
    }

    return modiBounds(mouse, table.rect, table)
  }

  private def PaintResult modiColWidth(int colIndex, Vec2 from, int height, Rect placeArg, Vec2 mouseDownedAt) {
    return new PaintResult() {
      override getPlace() { placeArg }

      override isHasOper() { true }

      override createOper(Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        if(d.x == 0) return null
        var nowWidths = new ArrayList<Integer>
        if (table.colWidths != null) {
          for (str : table.colWidths.split('\\|')) {
            try {
              nowWidths += Integer.valueOf(str)
            } catch (NumberFormatException e) {
              nowWidths += MIN_COL_WIDTH
            }
          }
        }
        while (nowWidths.size <= colIndex) {
          nowWidths += MIN_COL_WIDTH
        }
        var width = nowWidths.get(colIndex)
        width = width + d.x
        if(width < MIN_COL_WIDTH) width = MIN_COL_WIDTH
        nowWidths.set(colIndex, width)

        return new OperModify(COL_WIDTHS_SETTER, nowWidths.join('|'), table.id)
      }

      override getKursor() { Kursor.SIZEWE }

      override paintDrag(DrawPort dp, Vec2 mouseMovedTo) {
        var d = mouseMovedTo - mouseDownedAt
        dp.style.foreground = Kolor.GRAY
        dp.from(from + #[d.x, 0]).shift(0, height).line
      }
    }
  }

  static val COL_WIDTHS_SETTER = new ValueSetter() {
    override setValue(Object object, Object value) {
      var t = object as Table
      var ret = t.colWidths
      t.colWidths = value as String
      return ret
    }

    override getType() { String }

    override getName() { 'colWidths' }
  }

  private def static List<Line> extractLines(Table table) {

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

    val List<Integer> widths = new ArrayList
    for (str : (table.colWidths ?: '').split('\\|')) {
      try {
        widths += Integer.valueOf(str)
      } catch (NumberFormatException e) {
        widths += PaintTable.MIN_COL_WIDTH
      }
    }

    if (widths.size < colCount) {
      for (var i = 0, var C = colCount - widths.size; i < C; i++) {
        widths += PaintTable.MIN_COL_WIDTH
      }
    }

    for (line : ret) {
      for (var i = 0; i < colCount; i++) {
        line.cellList.get(i).width = widths.get(i)
      }
    }

    return ret
  }

  private def int calcCellHeight(Cell cell, Line line, TableStyle ts) {
    dp.font = line.extractFont(ts)
    var textSize = dp.str(cell.text).size

    var drawSize = ts.cellPadding?.apply(textSize) ?: textSize

    return drawSize.height
  }

  private def void drawCell(TableStyle ts, Rect tableRect, Vec2 p, Cell cell, Line line) {

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
