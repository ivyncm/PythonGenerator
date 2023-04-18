# -*- coding: utf-8 -*-

from qiskit import QuantumCircuit, QuantumRegister, ClassicalRegister
from numpy import pi

def Teleportation_Algorithm() -> QuantumCircuit:
	q0 = QuantumRegister(1, 'q0')
	q2 = QuantumRegister(1, 'q2')
	q1 = QuantumRegister(1, 'q1')
	
	msg1 = ClassicalRegister(1, 'msg1')
	register1 = ClassicalRegister(1, 'register1')
	
	Teleportation_Algorithm = QuantumCircuit(q0, q2, q1, msg1, register1)
	
	Teleportation_Algorithm.h(q1)
	Teleportation_Algorithm.cx(q1, q2)
	Teleportation_Algorithm.cx(q0, q1)
	Teleportation_Algorithm.h(q0)
	Teleportation_Algorithm.measure(q0, msg1)
	Teleportation_Algorithm.cz(q0, q2)
	Teleportation_Algorithm.measure(q1, register1)
	Teleportation_Algorithm.cx(q1, q2)
	
	return Teleportation_Algorithm
