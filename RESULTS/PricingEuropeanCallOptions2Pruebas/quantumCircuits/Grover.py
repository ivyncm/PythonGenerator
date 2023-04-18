# -*- coding: utf-8 -*-

from qiskit import QuantumCircuit, QuantumRegister, ClassicalRegister
from numpy import pi

def Grover() -> QuantumCircuit:
	q1 = QuantumRegister(1, 'q1')
	q0 = QuantumRegister(1, 'q0')
	
	register1 = ClassicalRegister(1, 'register1')
	register2 = ClassicalRegister(1, 'register2')
	
	Grover = QuantumCircuit(q1, q0, register1, register2)
	
	Grover.h(q0)
	Grover.h(q1)
	Grover.x(q0)
	Grover.x(q1)
	Grover.cz(q0, q1)
	Grover.x(q0)
	Grover.x(q1)
	Grover.h(q0)
	Grover.h(q1)
	Grover.z(q0)
	Grover.z(q1)
	Grover.cz(q0, q1)
	Grover.h(q0)
	Grover.h(q1)
	Grover.measure(q0, register1)
	Grover.measure(q1, register2)
	
	return Grover
