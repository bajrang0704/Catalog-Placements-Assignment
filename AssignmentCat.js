const fs = require('fs');

const testCases = JSON.parse(fs.readFileSync('input.json', 'utf-8'));

function parseInput(data) {
  const { n, k } = data.keys;

  const points = Object.keys(data)
    .filter((key) => !isNaN(parseInt(key))) 
    .map((key) => {
      const base = parseInt(data[key].base, 10);
      const value = parseInt(data[key].value, base);
      return { x: parseInt(key), y: value };
    });

  return points.slice(0, k); 
}

function lagrangeInterpolation(points) {
  const k = points.length;
  let c = 0;

  for (let i = 0; i < k; i++) {
    let xi = points[i].x;
    let yi = points[i].y;
    let li = 1;

    for (let j = 0; j < k; j++) {
      if (i !== j) {
        li *= -points[j].x / (xi - points[j].x);
      }
    }

    c += yi * li;
  }

  return c;
}

function findConstantTerm(data) {
  const points = parseInput(data);
  const constantTerm = lagrangeInterpolation(points);
  return constantTerm;
}

for (let i = 0; i < testCases.length; i++) {
  const data = testCases[i];

    const constant = findConstantTerm(data);
    console.log(`Constant term for test case [${i + 1}]`, constant);
}
