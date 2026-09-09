// Rewrites the "# Playground:" line at the top of every source under ../Compass.
//
//     node Tools/playground-links.mjs
//
// Nothing in the coursework depends on this. It exists only to keep the browser links in those
// files honest, and can be ignored by anyone reading the assignments.
//
// Run it after changing any of them; a link holds the program, so an edited file has a stale one.
//
// A link is what the site's Share button makes: the source gzipped, then base64 in the URL
// alphabet, in the fragment. A fragment never reaches a server, so nothing is stored anywhere and
// the link works for as long as the page does.
//
// **The playground compiles one file.** Where a program is spread across a folder — a model in
// one file and its test program in another, the way `javac *.java` allows — the files are folded
// into one for the link, each behind a divider naming where it came from. The folding is only in
// the link; the folder keeps its shape.
//
// **The link is generated from the source without its own "# Playground:" line**, so running this
// twice makes the same link rather than one that encodes the last one.

import { readFileSync, writeFileSync } from 'node:fs';
import { basename, dirname, join, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { gzipSync } from 'node:zlib';

/** Found from this file rather than the working directory, so the script runs from anywhere. */
const ported = resolve(dirname(fileURLToPath(import.meta.url)), '..', 'Compass');

/** Each runnable program, and the files it is made of. Order matters: a model before its use. */
const programs = [
    { name: 'Rectangle', folder: 'Assignment 1', files: ['Rectangle.cm', 'TestRectangle.cm'] },
    { name: 'DailyTemps', folder: 'Assignment 1', files: ['DailyTemps.cm', 'TestTemps.cm'] },
    { name: 'Vowels', folder: 'Assignment 2', files: ['Vowels.cm'] },
    { name: 'AverageGrades', folder: 'Assignment 2', files: ['AverageGrades.cm'] },
    { name: 'LinkedList', folder: 'Assignment 3', files: ['LinkedList.cm', 'TestLinkedList.cm'] },
    { name: 'HashFunctions', folder: 'Assignment 8', files: ['HashFunctions.cm'] },
    { name: 'ReachabilityMatrix', folder: 'Assignment 9', files: ['ReachabilityMatrix.cm'] },
];

/** What a link may carry, past which some chat and mail clients cut one. The site's own limit. */
const most = 8000;

const marker = '# Playground: ';

/** A file's text with any previous link line taken out, so a second run makes the same link. */
const withoutLink = (text) =>
    text.split('\n').filter(line => !line.startsWith(marker)).join('\n');

const divider = (name) => {
    const opening = `# ---- ${name} `;
    return opening + '-'.repeat(Math.max(4, 100 - opening.length));
};

for (const program of programs) {
    const paths = program.files.map(file => join(ported, program.folder, file));
    const sources = paths.map(path => withoutLink(readFileSync(path, 'utf8')));

    const folded = sources.length === 1
        ? sources[0].trimEnd()
        : sources
            .map((text, at) => `${divider(basename(paths[at]))}\n\n${text.trim()}`)
            .join('\n\n');

    const packed = gzipSync(Buffer.from(folded + '\n', 'utf8'), { level: 9 })
        .toString('base64')
        .replace(/\+/g, '-')
        .replace(/\//g, '_')
        .replace(/=+$/, '');

    const link = `https://compass.pluperfect.dev/playground#program=${packed}`;

    if (link.length > most) {
        console.error(`${program.name}: ${link.length} characters, past the ${most} a link holds.`);
        process.exitCode = 1;
        continue;
    }

    // Written into every file the program is made of, so whichever one is opened has the link.
    for (const [at, path] of paths.entries()) {
        const lines = withoutLink(readFileSync(path, 'utf8')).split('\n');

        // Under the assignment header, which ends at the first blank line.
        const header = lines.findIndex(line => line.trim() === '');

        lines.splice(header, 0, marker + link);
        writeFileSync(path, lines.join('\n'));

        if (at === 0) {
            console.log(`${program.name.padEnd(20)} ${link.length} characters`);
        }
    }
}
