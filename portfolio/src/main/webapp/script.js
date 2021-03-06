// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Fetches comments from the servers and adds them to the DOM.
 */
function getComments() {
    fetch('/data').then(response => response.json()).then((comments) => {
    const commentListElement = document.getElementById('comments-section');
    comments.forEach((comment) => {
      commentListElement.appendChild(createCommentElement(comment));
      commentListElement.appendChild(createScoreElement(comment));
    })
  });
}

/** Creates an <li> element containing comment. */
function createCommentElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = "addcomment";
  commentElement.innerText = comment['text'];
  return commentElement;
}

function createScoreElement(comment) {
  const commentElement = document.createElement('li');
  commentElement.className = "addscore";
  commentElement.innerText = comment['score'];
  return commentElement;
}

