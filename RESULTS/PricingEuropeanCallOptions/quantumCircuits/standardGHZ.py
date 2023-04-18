# -*- coding: utf-8 -*-

from qiskit import QuantumCircuit, QuantumRegister, ClassicalRegister
from numpy import pi

def standardGHZ() -> QuantumCircuit:
	q0 = QuantumRegister(1, 'q0')
	q1 = QuantumRegister(1, 'q1')
	q2 = QuantumRegister(1, 'q2')
	
	register1 = ClassicalRegister(1, 'register1')
	register2 = ClassicalRegister(1, 'register2')
	register3 = ClassicalRegister(1, 'register3')
	
	standardGHZ = QuantumCircuit(q0, q1, q2, register1, register2, register3)
	
	standardGHZ.h(q0)
	standardGHZ.cx(q0, q1)
	standardGHZ.ccx(q0, q1, q2)
	standardGHZ.measure(q1, register1)
	standardGHZ.measure(q0, register2)
	standardGHZ.measure(q2, register3)
	
	return standardGHZ
