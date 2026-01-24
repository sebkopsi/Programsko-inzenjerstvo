

INSERT INTO public."difficultyLevel" ("name") 
VALUES 
  ('beginner'),
  ('intermediate'),
  ('advanced');

INSERT INTO public."instructorProfile" (
  biography, username, specialization
) 
VALUES 
  (
    'Lorem ', 'Josip Josipovic', 'Croatian Cuisine'
  );

INSERT INTO public."enrolleeProfile" (username) 
VALUES 
  ('test'),
  ('instructor'),
  ('admin');


INSERT INTO public."tag" (
  name, category
)
VALUES
  ('cooking', 'general'),
  ('italian', 'favorite cuisines'),
  ('pasta', 'general'),
  ('gluten', 'general'),
  ('sauces', 'general'),
  ('brocolli','dietary preferences'),
  ('pizza','dietary preferences'),
  ('american','favorite cuisines'),
  ('poison','allergies')
  ;


INSERT INTO public."user" (
   firstname, surname, password_hash, 
  email, "createdAt", "enrolleeId", 
  "instructorId", "isVerified",
  "isAdmin", "isModerator"
)
VALUES 
  (
     'test', 'test', '$argon2id$v=19$m=8192,t=3,p=1$A7Vw2BZ7D4o2YADMheJe2g$RpPi3ozewwv/ebhqgr92qD1ccckNvj8QXNFpoi3ai3c', 
    'test@testmail.com', '2026-01-15 19:11:48.945', 1, 
    NULL, false,
    false, false
  ),
  (
    'instructor', 'instructor', '$argon2id$v=19$m=8192,t=3,p=1$DdjjsyoOYhjzjdPMXeGc1w$xztykIEvw1hq3nG/0l5SaogiR8x/Isv/ZQ6TWyKeqTA', 
    'instructor@testmail.com', '2026-01-15 19:18:40.723', 2, 
    1, false,
    false, false
  ),
  (
    'admin', 'admin', '$argon2id$v=19$m=8192,t=3,p=1$D4PFX/lQO1eiydI6FrP+9g$y9x1omRk+XJvmhC8a9FB7G6d7GIxj2loMpC2IZP2s9I',
    'admin@testmail.com', '2026-01-20 09:40:25.429 +0100', 3,
    NULL, false,
    true, true
  );

INSERT INTO public.userTag (
  "userId", "tagId", preferred
)
VALUES
  (1,6,false),
  (1,7,true),
  (1,8,true),
  (1,9,false);

INSERT INTO public.course (
   "name", "creatorId", description, 
  "difficultyId", "avgRating"
) 
VALUES 
  (
    'Mastering Italian Cooking', 2, 'Learn the fundamentals of Italian cuisine, from classic pasta dishes to traditional desserts, with step-by-step instructions.', 
    1, 0.00
  );

INSERT INTO public."courseTag" (
  "courseId", "tagId"
)
VALUES
  (1,1),
  (1,2),
  (1,3),
  (1,4),
  (1,5);

INSERT INTO public."module" ("name", "courseId", orderindex) 
VALUES 
  ('Pasta Perfection', 1, 1);
INSERT INTO public.lecture (
  "writtenSteps", "preparationTime", 
  "cookingTime", "name", "moduleId", 
  "videoId", "difficultyId", "creatorId", 
  quizjson, minscore
) 
VALUES 
  (
    '# Classic Spaghetti with Tomato Sauce
**Preparation Time:** 10 minutes  
**Cooking Time:** 15 minutes  
**Difficulty:** Intermediate  

---

## Step-by-Step Instructions

### Step 1: Prepare the Water and Pasta
Begin by filling a large pot with fresh water and bringing it to a rolling boil over high heat. Once the water is bubbling, add a generous pinch of salt to season it. This step is crucial, as the salted water will enhance the flavor of the pasta from the inside out. Gently add the pasta to the boiling water and stir occasionally to prevent it from sticking. Cook the pasta according to the package instructions, ensuring it reaches a perfect al dente texture — tender but still slightly firm in the center.  

### Step 2: Prepare the Sauce Base
While the pasta is cooking, take a medium-sized pan and heat a couple of tablespoons of extra virgin olive oil over medium heat. Add finely chopped garlic and sauté until it releases a fragrant aroma, being careful not to let it burn. The garlic should become golden and slightly soft, creating a flavorful base for the sauce.  

### Step 3: Simmer the Tomato Sauce
Once the garlic is ready, pour in fresh or canned tomato sauce and stir to combine. Allow the sauce to simmer gently on low heat for about 10 minutes. This step is essential to let the flavors meld together — the acidity of the tomatoes softens, the garlic infuses, and the sauce thickens slightly. Taste periodically and season with a pinch of salt and a dash of freshly ground black pepper, adjusting to your preference.  

### Step 4: Combine Pasta and Sauce
When the pasta reaches the desired texture, carefully drain it, reserving a small cup of the pasta water. Add the drained pasta directly into the simmering sauce and toss thoroughly to coat every strand. If the sauce feels too thick, add a few tablespoons of the reserved pasta water to loosen it and enhance its creaminess.  

### Step 5: Serve and Garnish
Plate the pasta while it is still hot. Sprinkle with freshly grated Parmesan cheese and, if desired, add a few fresh basil leaves for aroma and presentation. Serve immediately for the best experience, pairing it with a light salad or a slice of crusty bread. Enjoy the rich, comforting flavors of this classic Italian dish that balances simplicity with depth.  
', 
    '00:10:00' :: interval, '00:30:00' :: interval, 
    'Classic Spaghetti with Tomato Sauce', 
    1, NULL, 1, 2, '{
  "questions": [
    {
      "id": 1,
      "type": "single",
      "value": "How long should the tomato sauce be simmered?"
    },
    {
      "id": 2,
      "type": "multiple",
      "value": "Which ingredients are added to make the classic spaghetti with tomato sauce?"
    },
    {
      "id": 3,
      "type": "text",
      "value": "Why is it important to cook the pasta in salted water?"
    }
  ],
  "options": [
    {
      "id": 1,
      "questionId": 1,
      "value": "5 minutes",
      "correct": false
    },
    {
      "id": 2,
      "questionId": 1,
      "value": "10 minutes",
      "correct": true
    },
    {
      "id": 3,
      "questionId": 1,
      "value": "15 minutes",
      "correct": false
    },
    {
      "id": 4,
      "questionId": 1,
      "value": "20 minutes",
      "correct": false
    },
    {
      "id": 5,
      "questionId": 2,
      "value": "Pasta",
      "correct": true
    },
    {
      "id": 6,
      "questionId": 2,
      "value": "Tomato sauce",
      "correct": true
    },
    {
      "id": 7,
      "questionId": 2,
      "value": "Garlic",
      "correct": true
    },
    {
      "id": 8,
      "questionId": 2,
      "value": "Olive oil",
      "correct": true
    },
    {
      "id": 9,
      "questionId": 2,
      "value": "Cream",
      "correct": false
    },
    {
      "id": 10,
      "questionId": 2,
      "value": "Basil",
      "correct": true
    }
  ]
}
' :: json, 
    50
  );

INSERT INTO public.request  (
   "title", "content", 
   "type", "sentByUserId", 
   "reportedUserId", "targetCourseId"
)
VALUES 
  (
	'I want to be an instuructor plz', 'This is the content Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
  	'promoteInstructor', 1,
  	NULL, NULL
  ),
  (
	'Im reporting mr test', 'This is the content Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
  	'report', 2,
  	1, NULL
  ),
  (
	'Update my course plz', 'This is the content Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.',
  	'updateCourse', 2,
  	NULL, 1
  );

INSERT INTO public."enrolledCourse" (
	"courseId", "userId",
	"completionPercentage", "certificateId"
)
VALUES
(
	1,1,
	75, null
);