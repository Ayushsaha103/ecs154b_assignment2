// This file contains ALU control logic.

package dinocpu.components

import chisel3._
import chisel3.util._

/**
 * The ALU control unit
 *
 * Input:  aluop        Specifying the type of instruction using ALU
 *                          . 0 for none of the below
 *                          . 1 for arithmetic instruction types (R-type or I-type)
 *                          . 2 for non-arithmetic instruction types that uses ALU (auipc/jal/jarl/Load/Store)
 * Input:  arth_type    The type of instruction (0 for R-type, 1 for I-type)
 * Input:  int_length   The integer length (0 for 64-bit, 1 for 32-bit)
 * Input:  funct7       The most significant bits of the instruction.
 * Input:  funct3       The middle three bits of the instruction (12-14).
 *
 * Output: operation    What we want the ALU to do.
 *
 * For more information, see Section 4.4 and A.5 of Patterson and Hennessy.
 * This is loosely based on figure 4.12
 */
class ALUControl extends Module {
  val io = IO(new Bundle {
    val aluop       = Input(UInt(2.W))
    val arth_type   = Input(UInt(1.W))
    val int_length    = Input(UInt(1.W))
    val funct7      = Input(UInt(7.W))
    val funct3      = Input(UInt(3.W))

    val operation   = Output(UInt(5.W))
  })

  io.operation := "b11111".U // Invalid

  // Your code goes here
  when(io.funct7 === "b0000000".U) {  // funct7 = 0000000

    when (io.funct3 === "b000".U) { // funct3 = 000
      when (io.int_length === false.B) {    
        io.operation := "b00000".U      // add
      }
      when (io.int_length === true.B) {    
        io.operation := "b10000".U      // addw (todo)
      }
    }
    when (io.funct3 === "b001".U) {   // funct3 = 001
      when (io.int_length === false.B) {    
        io.operation := "b01010".U      // sll
      }
      when (io.int_length === true.B) {    
        io.operation := "b11010".U      // sllw (todo)
      }
    }
    when (io.funct3 === "b101".U) {   // funct3 = 101
      when (io.int_length === false.B) {    
        io.operation := "b01011".U      // srl
      }
      when (io.int_length === true.B) {    
        io.operation := "b11011".U      // srlw (todo)
      }
    }
    when (io.funct3 === "b010".U && io.int_length === false.B) {
      io.operation := "b01100".U   // slt
    }
    when (io.funct3 === "b011".U && io.int_length === false.B) {
      io.operation := "b01111".U   // sltu
    }
    when (io.funct3 === "b100".U && io.int_length === false.B) {
      io.operation := "b01000".U   // xor
    }
    when (io.funct3 === "b110".U && io.int_length === false.B) {
      io.operation := "b00111".U   // or
    }
    when (io.funct3 === "b111".U && io.int_length === false.B) {
      io.operation := "b00101".U   // and
    }
  }
  when(io.funct7 === "b0100000".U) {  // funct7 = 0100000
    when (io.funct3 === "b000".U) {   // funct3 = 000
      when (io.int_length === false.B) {    
        io.operation := "b00001".U      // sub
      }
      when (io.int_length === true.B) { 
        io.operation := "b10001".U      // subw (todo)
      }
    }
    when (io.funct3 === "b101".U) {   // funct3 = 101
      when (io.int_length === false.B) {    
        io.operation := "b01001".U      // sra
      }
      when (io.int_length === true.B) {    
        io.operation := "b11001".U      // sraw (todo)
      }
    }
  }
  when(io.funct7 === "b0000001".U) {  // funct7 = 0000001
    when (io.funct3 === "b000".U) {   // funct3 = 000
      when (io.int_length === false.B) {    
        io.operation := "b00010".U      // mul
      }
      when (io.int_length === true.B) {    
        io.operation := "b10010".U      // mulw (todo)
      }
    }
    when (io.funct3 === "b001".U && io.int_length === false.B) {    
      io.operation := "b10101".U      // mulh
    }
    when (io.funct3 === "b010".U && io.int_length === false.B) {    
      io.operation := "b11000".U      // mulhsu
    }
    when (io.funct3 === "b011".U && io.int_length === false.B) {    
      io.operation := "b10111".U      // mulhu
    }
    when (io.funct3 === "b100".U) {   // funct3 = 100
      when (io.int_length === false.B) {    
        io.operation := "b00011".U      // div
      }
      when (io.int_length === true.B) {    
        io.operation := "b10011".U      // divw (todo)
      }
    }
    when (io.funct3 === "b101".U) {   // funct3 = 101
      when (io.int_length === false.B) {    
        io.operation := "b01101".U      // divu
      }
      when (io.int_length === true.B) {    
        io.operation := "b11101".U      // divuw (todo)
      }
    }
    when (io.funct3 === "b110".U) {   // funct3 = 110
      when (io.int_length === false.B) {    
        io.operation := "b00100".U      // rem
      }
      when (io.int_length === true.B) {    
        io.operation := "b10100".U      // remw (todo)
      }
    }
    when (io.funct3 === "b111".U) {   // funct3 = 111
      when (io.int_length === false.B) {    
        io.operation := "b01110".U      // remu
      }
      when (io.int_length === true.B) {    
        io.operation := "b11110".U      // remuw (todo)
      }
    }

  }
}
